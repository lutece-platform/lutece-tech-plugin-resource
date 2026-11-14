/*
 * Copyright (c) 2002-2026, City of Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.resource.web;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import fr.paris.lutece.plugins.resource.business.database.DatabaseResourceType;
import fr.paris.lutece.plugins.resource.service.action.IResourceAction;
import fr.paris.lutece.portal.util.mvc.utils.MVCMessage;
import fr.paris.lutece.util.ReferenceList;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.StringTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.core.HTMLOutputFormat;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;

/**
 * Renders the management pages of the resources and of the resource types with HTML auto-escaping (LUT-33153): the
 * info messages of the MVC controllers are strings, the error messages are objects with a message, and every value
 * typed by an administrator is written as text. The core macros are replaced by stand-ins that write their nested
 * content, and the attributes aButton receives.
 */
public class ResourceTemplatesRenderTest
{
    private static final String TEMPLATES_ROOT = "webapp/WEB-INF/templates";
    private static final String TEMPLATE_MANAGE_RESOURCES = "admin/plugins/resource/manage_resources.html";
    private static final String TEMPLATE_MANAGE_RESOURCE_TYPES = "admin/plugins/resource/manage_resource_types.html";
    /** Stand-ins of the core admin macros the templates call. */
    private static final String MACROS = "resource_test_macros.ftl";
    private static final String MACROS_SOURCE = "<#macro pageContainer rest...><#nested></#macro><#macro pageColumn rest...><#nested></#macro>"
            + "<#macro pageHeader rest...><#nested></#macro><#macro alert rest...><#nested></#macro><#macro tabs rest...><#nested></#macro>"
            + "<#macro tabList rest...><#nested></#macro><#macro tabLink rest...></#macro><#macro tabContent rest...><#nested></#macro>"
            + "<#macro row rest...><#nested></#macro><#macro columns rest...><#nested></#macro><#macro tform rest...><#nested></#macro>"
            + "<#macro input rest...></#macro><#macro formGroup rest...><#nested></#macro><#macro inputGroup rest...><#nested></#macro>"
            + "<#macro select rest...></#macro><#macro inputGroupItem rest...><#nested></#macro><#macro button rest...></#macro>"
            + "<#macro table rest...><table><#nested></table></#macro><#macro sort rest...></#macro><#macro paginationAdmin rest...></#macro>"
            + "<#macro aButton href='' title='' params={} rest...><a href=\"${href}\"<#list params as name, value> ${name}=\"${value}\"</#list>>${title}</a></#macro>"
            + "<#function safeUrl url fallback='#'><#return url?lower_case?starts_with( 'javascript:' )?then( fallback, url )></#function>";

    private static final String PAYLOAD = "\"><svg onload=alert(1)>";
    private static final String PAYLOAD_ESCAPED = "&quot;&gt;&lt;svg onload=alert(1)&gt;";

    /**
     * The configuration of AbstractFreeMarkerTemplateService for HTML templates.
     *
     * @return the configuration
     * @throws IOException
     *             if the templates directory cannot be read
     */
    private static Configuration configuration( ) throws IOException
    {
        StringTemplateLoader macros = new StringTemplateLoader( );
        macros.putTemplate( MACROS, MACROS_SOURCE );
        Configuration cfg = new Configuration( Configuration.VERSION_2_3_32 );
        cfg.setOutputFormat( HTMLOutputFormat.INSTANCE );
        cfg.setAutoEscapingPolicy( Configuration.ENABLE_IF_DEFAULT_AUTO_ESCAPING_POLICY );
        cfg.setURLEscapingCharset( "UTF-8" );
        cfg.setTemplateLoader( new MultiTemplateLoader( new TemplateLoader [ ] {
                new FileTemplateLoader( new File( TEMPLATES_ROOT ) ), macros
        } ) );
        cfg.addAutoInclude( MACROS );

        return cfg;
    }

    /**
     * Renders a template of the plugin.
     *
     * @param strTemplate
     *            the template path
     * @param model
     *            the model
     * @return the rendering
     * @throws IOException
     *             if the template cannot be read
     * @throws TemplateException
     *             if the rendering fails
     */
    private static String render( String strTemplate, Map<String, Object> model ) throws IOException, TemplateException
    {
        StringWriter writer = new StringWriter( );
        configuration( ).getTemplate( strTemplate ).process( model, writer );

        return writer.toString( );
    }

    /**
     * The messages of a controller: the infos are strings (addInfo), the errors MVCMessage objects (addError).
     *
     * @param model
     *            the model to fill
     */
    private static void putMessages( Map<String, Object> model )
    {
        Set<String> setInfos = new LinkedHashSet<>( );
        setInfos.add( "info " + PAYLOAD );
        Set<MVCMessage> setErrors = new LinkedHashSet<>( );
        setErrors.add( new MVCMessage( "error " + PAYLOAD ) );
        model.put( "infos", setInfos );
        model.put( "errors", setErrors );
    }

    /**
     * Checks that the messages are written, as text.
     *
     * @param strHtml
     *            the rendering
     */
    private static void assertMessagesWrittenAsText( String strHtml )
    {
        assertTrue( strHtml.contains( "<div>info " + PAYLOAD_ESCAPED + "</div>" ), strHtml );
        assertTrue( strHtml.contains( "<div>error " + PAYLOAD_ESCAPED + "</div>" ), strHtml );
        assertFalse( strHtml.contains( "<svg" ), strHtml );
    }

    /**
     * The list of the resources: the messages, the names and the action links.
     *
     * @throws Exception
     *             if the rendering fails
     */
    @Test
    public void testManageResourcesWritesMessagesAndValuesAsText( ) throws Exception
    {
        Map<String, Object> model = new HashMap<>( );
        putMessages( model );
        ReferenceList refListTypes = new ReferenceList( );
        refListTypes.addItem( "room", "Room " + PAYLOAD );
        model.put( "refListResourceTypes", refListTypes );
        model.put( "listResources", List.of( Map.of( "idResource", "12&view=x", "resourceName", "name " + PAYLOAD, "resourceType", "room" ) ) );
        model.put( "list_actions", List.of( new TestAction( "javascript:alert(1)" ), new TestAction( "jsp/admin/Action.jsp?id=12&type=room" ) ) );
        model.put( "locale", Locale.FRENCH );
        model.put( "paginator", "" );

        String strHtml = render( TEMPLATE_MANAGE_RESOURCES, model );

        assertMessagesWrittenAsText( strHtml );
        assertTrue( strHtml.contains( "<td>name " + PAYLOAD_ESCAPED + "</td>" ), strHtml );
        assertTrue( strHtml.contains( "Room " + PAYLOAD_ESCAPED ), strHtml );
        assertTrue( strHtml.contains( "idResource=12%26view%3Dx\"" ), strHtml );
        assertTrue( strHtml.contains( "href=\"#\"" ), strHtml );
        assertTrue( strHtml.contains( "href=\"jsp/admin/Action.jsp?id=12&amp;type=room\"" ), strHtml );
        assertFalse( strHtml.contains( "javascript:" ), strHtml );
    }

    /**
     * The list of the resource types: the messages, the descriptions and the links.
     *
     * @throws Exception
     *             if the rendering fails
     */
    @Test
    public void testManageResourceTypesWritesMessagesAndValuesAsText( ) throws Exception
    {
        DatabaseResourceType resourceType = new DatabaseResourceType( );
        resourceType.setResourceTypeName( "a&b" );
        resourceType.setResourceTypeDescription( "description " + PAYLOAD );
        Map<String, Object> model = new HashMap<>( );
        putMessages( model );
        model.put( "list_resource_types", List.of( resourceType ) );
        model.put( "paginator", "" );

        String strHtml = render( TEMPLATE_MANAGE_RESOURCE_TYPES, model );

        assertMessagesWrittenAsText( strHtml );
        assertTrue( strHtml.contains( "<td>description " + PAYLOAD_ESCAPED + "</td>" ), strHtml );
        assertTrue( strHtml.contains( "resourceTypeName=a%26b\"" ), strHtml );
    }

    /**
     * An action of a module, its URL given by the test.
     */
    public static final class TestAction implements IResourceAction
    {
        private final String _strUrl;

        /**
         * Constructor
         *
         * @param strUrl
         *            the URL of the action
         */
        TestAction( String strUrl )
        {
            _strUrl = strUrl;
        }

        @Override
        public boolean canActionBePerformed( String strIdResource, String strResourceType )
        {
            return true;
        }

        @Override
        public String getActionUrl( String strIdResource, String strResourceType )
        {
            return _strUrl;
        }

        @Override
        public String getIcon( String strIdResource, String strResourceType )
        {
            return "calendar";
        }

        @Override
        public String getTitle( String strIdResource, String strResourceType, Locale locale )
        {
            return "Calendar " + PAYLOAD;
        }
    }
}
