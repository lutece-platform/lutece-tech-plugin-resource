/*
 * Copyright (c) 2002-2022, City of Paris
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
package fr.paris.lutece.plugins.resource.business.database;

import fr.paris.lutece.plugins.resource.service.ResourceCacheService;
import fr.paris.lutece.plugins.resource.service.ResourcePlugin;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;

import java.util.List;

/**
 * Database resource home
 */
public final class DatabaseResourceHome
{
    private static IDatabaseResourceDAO _dao = SpringContextService.getBean( IDatabaseResourceDAO.BEAN_NAME );
    private static Plugin _plugin = PluginService.getPlugin( ResourcePlugin.PLUGIN_NAME );

    /**
     * Default constructor
     */
    private DatabaseResourceHome( )
    {
        // Nothing to do
    }

    /**
     * Create a new database resource
     * 
     * @param resource
     *            The resource to creates
     */
    public static void create( DatabaseResource resource )
    {
        _dao.insert( resource, _plugin );
        ResourceCacheService.getInstance( ).putInCache( ResourceCacheService.getDatabaseResourceCacheKey( resource.getIdResource( ) ), resource.clone( ) );
    }

    /**
     * Updates a database resource
     * 
     * @param resource
     *            The resource
     */
    public static void update( DatabaseResource resource )
    {
        _dao.update( resource, _plugin );
        ResourceCacheService.getInstance( ).putInCache( ResourceCacheService.getDatabaseResourceCacheKey( resource.getIdResource( ) ), resource.clone( ) );
    }

    /**
     * Remove a resource from the database
     * 
     * @param nIdResource
     *            the id of the resource
     */
    public static void delete( int nIdResource )
    {
        _dao.delete( nIdResource, _plugin );
        ResourceCacheService.getInstance( ).removeKey( ResourceCacheService.getDatabaseResourceCacheKey( Integer.toString( nIdResource ) ) );
    }

    /**
     * Find a resource from its primary key
     * 
     * @param nIdResource
     *            the id of the resource
     * @return The resource, or null if no resource has the given primary key
     */
    public static DatabaseResource findByPrimaryKey( int nIdResource )
    {
        String strCacheKey = ResourceCacheService.getDatabaseResourceCacheKey( Integer.toString( nIdResource ) );
        DatabaseResource resource = (DatabaseResource) ResourceCacheService.getInstance( ).getFromCache( strCacheKey );

        if ( resource == null )
        {
            resource = _dao.findByPrimaryKey( nIdResource, _plugin );
            ResourceCacheService.getInstance( ).putInCache( strCacheKey, resource.clone( ) );
        }
        else
        {
            resource = resource.clone( );
        }

        return resource;
    }

    /**
     * Get the list of resources
     * 
     * @return The list of resources
     */
    public static List<DatabaseResource> findAll( )
    {
        return _dao.findAll( _plugin );
    }

    /**
     * Get the list of ids of resources
     * 
     * @param resourceSort
     *            The sort parameters
     * @return The list of ids of resources
     */
    public static List<Integer> findAllId( DatabaseResourceSort resourceSort )
    {
        return _dao.findAllId( resourceSort, _plugin );
    }

    /**
     * Get the list of database resources from an id list
     * 
     * @param listId
     *            The list of ids of resources to get
     * @param resourceSort
     *            The sort parameters
     * @return The list of resources
     */
    public static List<DatabaseResource> findByListId( List<Integer> listId, DatabaseResourceSort resourceSort )
    {
        return _dao.findByListId( listId, resourceSort, _plugin );
    }

    /**
     * Get the list of resources that have a given resource type
     * 
     * @param strResourceType
     *            The resource type
     * @return The list of resources, or an empty list if no resource has the given resource type
     */
    public static List<DatabaseResource> findByResourceType( String strResourceType )
    {
        return _dao.findByResourceType( strResourceType, _plugin );
    }
}
