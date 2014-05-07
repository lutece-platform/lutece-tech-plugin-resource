/*
 * Copyright (c) 2002-2014, Mairie de Paris
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
public final class DatabaseResourceTypeHome
{
    private static IDatabaseResourceTypeDAO _dao = SpringContextService.getBean( IDatabaseResourceTypeDAO.BEAN_NAME );
    private static Plugin _plugin = PluginService.getPlugin( ResourcePlugin.PLUGIN_NAME );

    /**
     * Default constructor
     */
    private DatabaseResourceTypeHome(  )
    {
        // Nothing to do
    }

    /**
     * insert a new resource type into the database
     * @param resourceType The resource type to creates
     */
    public static void insert( DatabaseResourceType resourceType )
    {
        _dao.insert( resourceType, _plugin );
        ResourceCacheService.getInstance(  )
                            .putInCache( ResourceCacheService.getResourceTypeCacheKey( 
                resourceType.getResourceTypeName(  ) ), resourceType.clone(  ) );
        ResourceCacheService.getInstance(  ).removeKey( ResourceCacheService.getResourceTypesListCacheKey(  ) );
    }

    /**
     * Updates a resource type
     * @param resourceType The resource
     */
    public static void update( DatabaseResourceType resourceType )
    {
        _dao.update( resourceType, _plugin );
        ResourceCacheService.getInstance(  )
                            .putInCache( ResourceCacheService.getResourceTypeCacheKey( 
                resourceType.getResourceTypeName(  ) ), resourceType.clone(  ) );
        ResourceCacheService.getInstance(  ).removeKey( ResourceCacheService.getResourceTypesListCacheKey(  ) );
    }

    /**
     * Remove a resource type from the database
     * @param strResourceType the name of the resource type
     */
    public static void delete( String strResourceType )
    {
        _dao.delete( strResourceType, _plugin );
        ResourceCacheService.getInstance(  ).removeKey( ResourceCacheService.getResourceTypeCacheKey( strResourceType ) );
        ResourceCacheService.getInstance(  ).removeKey( ResourceCacheService.getResourceTypesListCacheKey(  ) );
    }

    /**
     * Find a resource from its primary key
     * @param strResourceType the id of the resource
     * @return The resource type, or null if no resource type has the given
     *         primary key
     */
    public static DatabaseResourceType findByPrimaryKey( String strResourceType )
    {
        String strCacheKey = ResourceCacheService.getResourceTypeCacheKey( strResourceType );
        DatabaseResourceType resourceType = (DatabaseResourceType) ResourceCacheService.getInstance(  )
                                                                                       .getFromCache( strCacheKey );

        if ( resourceType == null )
        {
            resourceType = _dao.findByPrimaryKey( strResourceType, _plugin );

            if ( resourceType != null )
            {
                ResourceCacheService.getInstance(  ).putInCache( strCacheKey, resourceType.clone(  ) );
            }
        }
        else
        {
            resourceType = resourceType.clone(  );
        }

        return resourceType;
    }

    /**
     * Get the list of resource types
     * @return The list of resource types
     */
    public static List<DatabaseResourceType> findAll(  )
    {
        return _dao.findAll( _plugin );
    }

    /**
     * Get the list of resource types
     * @return the list of resource types
     */
    public static List<String> getResourceTypesList(  )
    {
        return _dao.getResourceTypesList( _plugin );
    }
}
