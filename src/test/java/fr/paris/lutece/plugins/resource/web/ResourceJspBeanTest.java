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

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.paris.lutece.plugins.resource.business.database.DatabaseResource;
import fr.paris.lutece.plugins.resource.business.database.DatabaseResourceHome;
import fr.paris.lutece.plugins.resource.business.database.DatabaseResourceType;
import fr.paris.lutece.plugins.resource.business.database.DatabaseResourceTypeHome;
import fr.paris.lutece.portal.business.user.AdminUser;
import fr.paris.lutece.test.AdminUserUtils;
import fr.paris.lutece.test.LuteceTestCase;
import fr.paris.lutece.test.mocks.MockHttpServletRequest;
import jakarta.inject.Inject;

/**
 * ResourceJspBean Test: a resource without a name is neither created nor saved
 */
public class ResourceJspBeanTest extends LuteceTestCase
{
    private static final String RESOURCE_NAME = "ResourceJspBeanTest";

    @Inject
    private ResourceJspBean _bean;
    private String _strResourceTypeName;

    @BeforeEach
    protected void createResourceType( )
    {
        _strResourceTypeName = "restest" + System.nanoTime( );
        DatabaseResourceType resourceType = new DatabaseResourceType( );
        resourceType.setResourceTypeName( _strResourceTypeName );
        resourceType.setResourceTypeDescription( RESOURCE_NAME );
        DatabaseResourceTypeHome.insert( resourceType );
    }

    @AfterEach
    protected void removeResourceType( )
    {
        for ( DatabaseResource resource : DatabaseResourceHome.findByResourceType( _strResourceTypeName ) )
        {
            DatabaseResourceHome.delete( Integer.parseInt( resource.getIdResource( ) ) );
        }
        DatabaseResourceTypeHome.delete( _strResourceTypeName );
    }

    @Test
    public void testDoCreateResourceWithoutNameReturnsToTheCreationScreen( ) throws Exception
    {
        MockHttpServletRequest request = getUserRequest( );
        request.addParameter( "action", "doCreateResource" );
        request.addParameter( "resourceType", _strResourceTypeName );
        request.addParameter( "resourceName", "" );
        RedirectRecordingResponse response = new RedirectRecordingResponse( );

        _bean.processController( request, response );

        assertTrue( DatabaseResourceHome.findByResourceType( _strResourceTypeName ).isEmpty( ), "no resource created" );
        assertNotNull( response.getRedirectLocation( ) );
        assertTrue( response.getRedirectLocation( ).contains( "view=viewCreateResource" ), response.getRedirectLocation( ) );
    }

    @Test
    public void testDoModifyResourceWithoutNameKeepsTheResource( ) throws Exception
    {
        DatabaseResource resource = new DatabaseResource( );
        resource.setResourceType( _strResourceTypeName );
        resource.setResourceName( RESOURCE_NAME );
        DatabaseResourceHome.create( resource );
        String strIdResource = DatabaseResourceHome.findByResourceType( _strResourceTypeName ).get( 0 ).getIdResource( );

        MockHttpServletRequest request = getUserRequest( );
        request.addParameter( "action", "doModifyResource" );
        request.addParameter( "idResource", strIdResource );
        request.addParameter( "resourceName", "" );
        RedirectRecordingResponse response = new RedirectRecordingResponse( );

        _bean.processController( request, response );

        assertEquals( RESOURCE_NAME, DatabaseResourceHome.findByPrimaryKey( Integer.parseInt( strIdResource ) ).getResourceName( ) );
        assertNotNull( response.getRedirectLocation( ) );
        assertTrue( response.getRedirectLocation( ).contains( "view=viewModifyResource" ), response.getRedirectLocation( ) );
    }

    private MockHttpServletRequest getUserRequest( )
    {
        MockHttpServletRequest request = new MockHttpServletRequest( );
        request.setMethod( "POST" );
        AdminUserUtils.registerAdminUserWithRight( request, new AdminUser( ), ResourceJspBean.RIGHT_MANAGE_RESOURCES );

        return request;
    }
}
