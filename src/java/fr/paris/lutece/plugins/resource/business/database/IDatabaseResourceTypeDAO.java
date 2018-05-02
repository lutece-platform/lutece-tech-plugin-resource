/*
 * Copyright (c) 2002-2017, Mairie de Paris
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
 * Interface of DAO of database resource type
 */
public interface IDatabaseResourceTypeDAO
{
    /**
     * The name of the bean of the DAO
     */
    String BEAN_NAME = "resource.databaseResourceTypeDAO";

    /**
     * insert a new resource type into the database
     * @param resourceType The resource type to creates
     * @param plugin The plugin
     */
    void insert( DatabaseResourceType resourceType, Plugin plugin );

    /**
     * Updates a resource type
     * @param resourceType The resource
     * @param plugin The plugin
     */
    void update( DatabaseResourceType resourceType, Plugin plugin );

    /**
     * Remove a resource type from the database
     * @param strResourceType the name of the resource type
     * @param plugin the plugin
     */
    void delete( String strResourceType, Plugin plugin );

    /**
     * Find a resource from its primary key
     * @param strResourceType the id of the resource
     * @param plugin The plugin
     * @return The resource type, or null if no resource type has the given
     *         primary key
     */
    DatabaseResourceType findByPrimaryKey( String strResourceType, Plugin plugin );

    /**
     * Get the list of resource types
     * @param plugin the plugin
     * @return The list of resource types
     */
    List<DatabaseResourceType> findAll( Plugin plugin );

    /**
     * Get the list of resource type names
     * @param plugin the plugin
     * @return the list of resource type names
     */
    List<String> getResourceTypesList( Plugin plugin );
}
