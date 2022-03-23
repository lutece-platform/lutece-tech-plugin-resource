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

import fr.paris.lutece.portal.service.plugin.Plugin;

import java.util.List;

/**
 * Interface of DAO to manage database resources
 */
public interface IDatabaseResourceDAO
{
    /**
     * The name of the bean of the DAO
     */
    String BEAN_NAME = "resource.databaseResourceDAO";

    /**
     * insert a new resource into the database
     * 
     * @param resource
     *            The resource to creates
     * @param plugin
     *            The plugin
     */
    void insert( DatabaseResource resource, Plugin plugin );

    /**
     * Updates a database resource
     * 
     * @param resource
     *            The resource
     * @param plugin
     *            The plugin
     */
    void update( DatabaseResource resource, Plugin plugin );

    /**
     * Remove a resource from the database
     * 
     * @param nIdResource
     *            the id of the resource
     * @param plugin
     *            the plugin
     */
    void delete( int nIdResource, Plugin plugin );

    /**
     * Find a resource from its primary key
     * 
     * @param nIdResource
     *            the id of the resource
     * @param plugin
     *            The plugin
     * @return The resource, or null if no resource has the given primary key
     */
    DatabaseResource findByPrimaryKey( int nIdResource, Plugin plugin );

    /**
     * Get the list of resources
     * 
     * @param plugin
     *            the plugin
     * @return The list of resources
     */
    List<DatabaseResource> findAll( Plugin plugin );

    /**
     * Get the list of resource ids
     * 
     * @param resourceSort
     *            The sort parameters
     * @param plugin
     *            The plugin
     * @return the list of resource ids
     */
    List<Integer> findAllId( DatabaseResourceSort resourceSort, Plugin plugin );

    /**
     * Get the list of database resources from an id list
     * 
     * @param listId
     *            The list of ids of resources to get
     * @param resourceSort
     *            The sort parameters
     * @param plugin
     *            The plugin
     * @return The list of resources
     */
    List<DatabaseResource> findByListId( List<Integer> listId, DatabaseResourceSort resourceSort, Plugin plugin );

    /**
     * Get the list of resources that have a given resource type
     * 
     * @param strResourceType
     *            The resource type
     * @param plugin
     *            The plugin
     * @return The list of resources, or an empty list if no resource has the given resource type
     */
    List<DatabaseResource> findByResourceType( String strResourceType, Plugin plugin );
}
