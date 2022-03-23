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

/**
 * Database resource sort DTO
 */
public final class DatabaseResourceSort
{
    private static final String SORT_COLUMN_ID = "id_resource";
    private static final String SORT_COLUMN_RESOURCE_TYPE = "resource_type";
    private static final String SORT_COLUMN_RESOURCE_NAME = "resource_name";
    private static final DatabaseResourceSort DEFAULT_SORT = new DatabaseResourceSort( SORT_COLUMN_ID, true );
    private String _strSort;
    private boolean _bSortAsc;

    /**
     * Creates a new database resource sort
     * 
     * @param strSort
     *            The sort
     * @param bSortAsc
     *            True to sort ascending, false otherwise
     */
    private DatabaseResourceSort( String strSort, boolean bSortAsc )
    {
        this._strSort = strSort;
        this._bSortAsc = bSortAsc;
    }

    /**
     * Get the sort column name
     * 
     * @return The sort column name
     */
    public String getSort( )
    {
        return _strSort;
    }

    /**
     * Check if the sort should be ascending or descending
     * 
     * @return True to sort ascending, false otherwise
     */
    public boolean getSortAsc( )
    {
        return _bSortAsc;
    }

    /**
     * Get a sort from a column name
     * 
     * @param strSort
     *            The sort to get
     * @param bSortAsc
     *            True to sort ascending, false otherwise
     * @return The sort, or the default sort if the specified one does not match any of the columns
     */
    public static DatabaseResourceSort getDatabaseResourceSort( String strSort, boolean bSortAsc )
    {
        if ( SORT_COLUMN_ID.equals( strSort ) || SORT_COLUMN_RESOURCE_TYPE.equals( strSort ) || SORT_COLUMN_RESOURCE_NAME.equals( strSort ) )
        {
            return new DatabaseResourceSort( strSort, bSortAsc );
        }

        return DEFAULT_SORT;
    }
}
