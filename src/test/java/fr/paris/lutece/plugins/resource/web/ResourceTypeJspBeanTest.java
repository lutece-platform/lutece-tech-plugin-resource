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

import java.util.Locale;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.paris.lutece.plugins.resource.business.database.DatabaseResourceType;
import fr.paris.lutece.plugins.resource.business.database.DatabaseResourceTypeHome;
import fr.paris.lutece.portal.business.user.AdminUser;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.test.AdminUserUtils;
import fr.paris.lutece.test.LuteceTestCase;
import fr.paris.lutece.test.mocks.MockHttpServletRequest;
import jakarta.inject.Inject;

/**
 * ResourceTypeJspBean Test: a resource type created twice and an unknown resource type give an error message
 */
public class ResourceTypeJspBeanTest extends LuteceTestCase
{
    private static final String MESSAGE_UNKNOWN_RESOURCE_TYPE = "resource.removeResourceType.unknownResourceType";
    private static final String DESCRIPTION = "ResourceTypeJspBeanTest";

    @Inject
    private ResourceTypeJspBean _bean;
    private String _strResourceTypeName;

    @BeforeEach
    protected void createResourceType( )
    {
        _strResourceTypeName = "restypetest" + System.nanoTime( );
        DatabaseResourceType resourceType = new DatabaseResourceType( );
        resourceType.setResourceTypeName( _strResourceTypeName );
        resourceType.setResourceTypeDescription( DESCRIPTION );
        DatabaseResourceTypeHome.insert( resourceType );
    }

    @AfterEach
    protected void removeResourceType( )
    {
        DatabaseResourceTypeHome.delete( _strResourceTypeName );
    }

    @Test
    public void testDoCreateResourceTypeWithAnExistingNameReturnsToTheCreationScreen( ) throws Exception
    {
        MockHttpServletRequest request = getUserRequest( "POST" );
        request.addParameter( "action", "doCreateResourceType" );
        request.addParameter( "resourceTypeName", _strResourceTypeName );
        request.addParameter( "resourceTypeDescription", "other description" );
        RedirectRecordingResponse response = new RedirectRecordingResponse( );

        _bean.processController( request, response );

        assertNotNull( response.getRedirectLocation( ) );
        assertTrue( response.getRedirectLocation( ).contains( "view=viewCreateResourceType" ), response.getRedirectLocation( ) );
        assertEquals( DESCRIPTION, DatabaseResourceTypeHome.findByPrimaryKey( _strResourceTypeName ).getResourceTypeDescription( ) );
    }

    @Test
    public void testConfirmRemoveOfAnUnknownResourceTypeShowsTheError( ) throws Exception
    {
        MockHttpServletRequest request = getUserRequest( "GET" );
        request.addParameter( "view", "viewConfirmRemoveResourceType" );
        request.addParameter( "resourceTypeName", _strResourceTypeName + "unknown" );
        RedirectRecordingResponse response = new RedirectRecordingResponse( );

        String strHtml = _bean.processController( request, response );

        assertNull( response.getRedirectLocation( ) );
        assertNotNull( strHtml );
        assertTrue( strHtml.contains( I18nService.getLocalizedString( MESSAGE_UNKNOWN_RESOURCE_TYPE, Locale.FRENCH ) )
                || strHtml.contains( I18nService.getLocalizedString( MESSAGE_UNKNOWN_RESOURCE_TYPE, Locale.ENGLISH ) ), strHtml );
    }

    @Test
    public void testModifyViewOfAnUnknownResourceTypeShowsTheError( ) throws Exception
    {
        MockHttpServletRequest request = getUserRequest( "GET" );
        request.addParameter( "view", "viewModifyResourceType" );
        request.addParameter( "resourceTypeName", _strResourceTypeName + "unknown" );
        RedirectRecordingResponse response = new RedirectRecordingResponse( );

        String strHtml = _bean.processController( request, response );

        assertNotNull( strHtml );
        assertTrue( strHtml.contains( I18nService.getLocalizedString( MESSAGE_UNKNOWN_RESOURCE_TYPE, Locale.FRENCH ) )
                || strHtml.contains( I18nService.getLocalizedString( MESSAGE_UNKNOWN_RESOURCE_TYPE, Locale.ENGLISH ) ), strHtml );
    }

    private MockHttpServletRequest getUserRequest( String strMethod )
    {
        MockHttpServletRequest request = new MockHttpServletRequest( );
        request.setMethod( strMethod );
        AdminUserUtils.registerAdminUserWithRight( request, new AdminUser( ), ResourceJspBean.RIGHT_MANAGE_RESOURCES );

        return request;
    }
}
