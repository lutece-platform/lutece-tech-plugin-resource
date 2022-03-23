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
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.util.sql.DAOUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * DAO for database resources
 */
public class DatabaseResourceDAO implements IDatabaseResourceDAO
{
    // Select
    private static final String SQL_QUERY_NEW_PRIMARY_KEY = " SELECT MAX(id_resource) FROM resource_resource ";
    private static final String SQL_QUERY_SELECT_RESOURCE = " SELECT id_resource, resource_type, resource_name FROM resource_resource ";
    private static final String SQL_QUERY_SELECT_RESOURCE_BY_ID = SQL_QUERY_SELECT_RESOURCE + " WHERE id_resource = ?  ";
    private static final String SQL_QUERY_SELECT_RESOURCE_ID = " SELECT id_resource FROM resource_resource ";
    private static final String SQL_QUERY_SELECT_RESOURCE_LIST_BY_ID = SQL_QUERY_SELECT_RESOURCE + " WHERE id_resource IN ( ";
    private static final String SQL_QUERY_SELECT_RESOURCE_BY_RESOURCE_TYPE = SQL_QUERY_SELECT_RESOURCE + " WHERE resource_type = ? ";

    // Update, insert, delete
    private static final String SQL_QUERY_INSERT_RESOURCE = " INSERT INTO resource_resource( id_resource, resource_type, resource_name ) VALUES (?,?,?) ";
    private static final String SQL_QUERY_UPDATE_RESOURCE = " UPDATE resource_resource SET resource_type = ?, resource_name = ? WHERE id_resource = ? ";
    private static final String SQL_QUERY_DELETE_RESOURCE = " DELETE FROM resource_resource WHERE id_resource = ? ";

    // Other
    private static final String ORDER_BY = " ORDER BY ";
    private static final String ORDER_BY_ASCENDING = " ASC ";
    private static final String ORDER_BY_DESCENDING = " DESC ";
    private static final String CONSTANT_QUESTION_MARK = "?";
    private static final String CONSTANT_COMMA = ",";
    private static final String CONSTANT_CLOSE_PARENTHESIS = ")";

    /**
     * Get a new primary key. This method should only be called by synchronized methods
     * 
     * @param plugin
     *            The plugin
     * @return The new value of the primary key
     */
    private int newPrimaryKey( Plugin plugin )
    {
        int nRes;
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_NEW_PRIMARY_KEY, plugin ) )
        {
            daoUtil.executeQuery( );

            if ( daoUtil.next( ) )
            {
                nRes = daoUtil.getInt( 1 ) + 1;
            }
            else
            {
                nRes = 1;
            }
        }

        return nRes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void insert( DatabaseResource resource, Plugin plugin )
    {
        int nId = newPrimaryKey( plugin );
        resource.setIdResource( nId );

        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT_RESOURCE, plugin ) )
        {
            int nIndex = 1;
            daoUtil.setInt( nIndex++, nId );
            daoUtil.setString( nIndex++, resource.getResourceType( ) );
            daoUtil.setString( nIndex, resource.getResourceName( ) );
            daoUtil.executeUpdate( );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update( DatabaseResource resource, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE_RESOURCE, plugin ) )
        {
            int nIndex = 1;

            try
            {
                daoUtil.setString( nIndex++, resource.getResourceType( ) );
                daoUtil.setString( nIndex++, resource.getResourceName( ) );
                daoUtil.setInt( nIndex, Integer.parseInt( resource.getIdResource( ) ) );
                daoUtil.executeUpdate( );
            }
            catch( NumberFormatException e )
            {
                AppLogService.error( e.getMessage( ), e );
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete( int nIdResource, Plugin plugin )
    {
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE_RESOURCE, plugin ) )
        {
            daoUtil.setInt( 1, nIdResource );
            daoUtil.executeUpdate( );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatabaseResource findByPrimaryKey( int nIdResource, Plugin plugin )
    {
        DatabaseResource resource = null;
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_RESOURCE_BY_ID, plugin ) )
        {
            daoUtil.setInt( 1, nIdResource );
            daoUtil.executeQuery( );

            if ( daoUtil.next( ) )
            {
                resource = getResourceFromDAO( daoUtil );
            }
        }

        return resource;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DatabaseResource> findAll( Plugin plugin )
    {
        List<DatabaseResource> listResources = new ArrayList<>( );
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_RESOURCE, plugin ) )
        {
            daoUtil.executeQuery( );

            while ( daoUtil.next( ) )
            {
                listResources.add( getResourceFromDAO( daoUtil ) );
            }
        }

        return listResources;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Integer> findAllId( DatabaseResourceSort resourceSort, Plugin plugin )
    {
        List<Integer> listResourcesId = new ArrayList<>( );
        StringBuilder sbSql = new StringBuilder( SQL_QUERY_SELECT_RESOURCE_ID );
        sbSql.append( ORDER_BY );
        sbSql.append( resourceSort.getSort( ) );
        sbSql.append( resourceSort.getSortAsc( ) ? ORDER_BY_ASCENDING : ORDER_BY_DESCENDING );

        try ( DAOUtil daoUtil = new DAOUtil( sbSql.toString( ), plugin ) )
        {
            daoUtil.executeQuery( );

            while ( daoUtil.next( ) )
            {
                listResourcesId.add( daoUtil.getInt( 1 ) );
            }
        }

        return listResourcesId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DatabaseResource> findByListId( List<Integer> listId, DatabaseResourceSort resourceSort, Plugin plugin )
    {
        if ( ( listId == null ) || ( listId.isEmpty( ) ) )
        {
            return new ArrayList<>( );
        }

        StringBuilder sbSql = new StringBuilder( SQL_QUERY_SELECT_RESOURCE_LIST_BY_ID );

        for ( int i = 0; i < listId.size( ); i++ )
        {
            if ( i > 0 )
            {
                sbSql.append( CONSTANT_COMMA );
            }

            sbSql.append( CONSTANT_QUESTION_MARK );
        }

        sbSql.append( CONSTANT_CLOSE_PARENTHESIS );

        sbSql.append( ORDER_BY );
        sbSql.append( resourceSort.getSort( ) );
        sbSql.append( resourceSort.getSortAsc( ) ? ORDER_BY_ASCENDING : ORDER_BY_DESCENDING );

        List<DatabaseResource> listResources = new ArrayList<>( );
        try ( DAOUtil daoUtil = new DAOUtil( sbSql.toString( ), plugin ) )
        {
            int nIndex = 1;

            for ( int nId : listId )
            {
                daoUtil.setInt( nIndex++, nId );
            }

            daoUtil.executeQuery( );

            while ( daoUtil.next( ) )
            {
                listResources.add( getResourceFromDAO( daoUtil ) );
            }
        }

        return listResources;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<DatabaseResource> findByResourceType( String strResourceType, Plugin plugin )
    {
        List<DatabaseResource> listResources = new ArrayList<>( );
        try ( DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_RESOURCE_BY_RESOURCE_TYPE, plugin ) )
        {
            daoUtil.setString( 1, strResourceType );
            daoUtil.executeQuery( );

            while ( daoUtil.next( ) )
            {
                listResources.add( getResourceFromDAO( daoUtil ) );
            }
        }

        return listResources;
    }

    /**
     * Get a database resource from a DAO. <br />
     * The call to the DAOUtil.next( ) must have been made before this method is closed. <br />
     * The DAOUtil will not be free by this method.
     * 
     * @param daoUtil
     *            The DAOUtil to read data from
     * @return The database resource
     */
    private DatabaseResource getResourceFromDAO( DAOUtil daoUtil )
    {
        DatabaseResource resource = new DatabaseResource( );
        int nIndex = 1;

        resource.setIdResource( daoUtil.getInt( nIndex++ ) );
        resource.setResourceType( daoUtil.getString( nIndex++ ) );
        resource.setResourceName( daoUtil.getString( nIndex ) );

        return resource;
    }
}
