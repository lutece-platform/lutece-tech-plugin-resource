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

import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * DAO for database resource type
 */
public class DatabaseResourceTypeDAO implements IDatabaseResourceTypeDAO
{
    private static final String SQL_QUERY_SELECT_RESOURCE_TYPE = " SELECT resource_type_name, resource_type_description FROM resource_resource_type ";
    private static final String SQL_QUERY_SELECT_RESOURCE_TYPE_BY_ID = SQL_QUERY_SELECT_RESOURCE_TYPE
            + " WHERE resource_type_name = ?  ";
    private static final String SQL_QUERY_SELECT_RESOURCE_TYPE_NAMES = " SELECT resource_type_name FROM resource_resource_type ";

    private static final String SQL_QUERY_INSERT_RESOURCE_TYPE = " INSERT INTO resource_resource_type( resource_type_name, resource_type_description ) VALUES (?,?) ";
    private static final String SQL_QUERY_UPDATE_RESOURCE_TYPE = " UPDATE resource_resource_type SET resource_type_description = ? WHERE resource_type_name = ? ";
    private static final String SQL_QUERY_DELETE_RESOURCE_TYPE = " DELETE FROM resource_resource_type WHERE resource_type_name = ? ";

    /**
     * {@inheritDoc}
     */
    @Override
    public void insert( DatabaseResourceType resourceType, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT_RESOURCE_TYPE, plugin );
        int nIndex = 1;
        daoUtil.setString( nIndex++, resourceType.getResourceTypeName( ) );
        daoUtil.setString( nIndex, resourceType.getResourceTypeDescription( ) );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update( DatabaseResourceType resourceType, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE_RESOURCE_TYPE, plugin );
        int nIndex = 1;
        daoUtil.setString( nIndex++, resourceType.getResourceTypeDescription( ) );
        daoUtil.setString( nIndex, resourceType.getResourceTypeName( ) );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete( String strResourceType, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_RESOURCE_TYPE, plugin );
        daoUtil.setString( 1, strResourceType );
        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatabaseResourceType findByPrimaryKey( String strResourceType, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_RESOURCE_TYPE_BY_ID, plugin );
        daoUtil.setString( 1, strResourceType );
        daoUtil.executeQuery( );
        DatabaseResourceType resourceType = null;
        if ( daoUtil.next( ) )
        {
            resourceType = getResourceFromDAO( daoUtil );
        }

        daoUtil.free( );
        return resourceType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DatabaseResourceType> findAll( Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_RESOURCE_TYPE, plugin );
        daoUtil.executeQuery( );
        List<DatabaseResourceType> listResourceTypes = new ArrayList<DatabaseResourceType>( );
        while ( daoUtil.next( ) )
        {
            listResourceTypes.add( getResourceFromDAO( daoUtil ) );
        }

        daoUtil.free( );
        return listResourceTypes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getResourceTypesList( Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_RESOURCE_TYPE_NAMES, plugin );
        daoUtil.executeQuery( );
        List<String> listResourceTypes = new ArrayList<String>( );
        while ( daoUtil.next( ) )
        {
            listResourceTypes.add( daoUtil.getString( 1 ) );
        }

        daoUtil.free( );
        return listResourceTypes;
    }

    /**
     * Get a database resource type from a DAO. <br />
     * The call to the DAOUtil.next( ) must
     * have been made before this method is closed. <br />
     * The DAOUtil will not be free by this method.
     * @param daoUtil The DAOUtil to read data from
     * @return The database resource type
     */
    private DatabaseResourceType getResourceFromDAO( DAOUtil daoUtil )
    {
        DatabaseResourceType resourceType = new DatabaseResourceType( );
        int nIndex = 1;

        resourceType.setResourceTypeName( daoUtil.getString( nIndex++ ) );
        resourceType.setResourceTypeDescription( daoUtil.getString( nIndex ) );

        return resourceType;
    }

}
