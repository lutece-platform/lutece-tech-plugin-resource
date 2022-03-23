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
package fr.paris.lutece.plugins.resource.service.provider;

import fr.paris.lutece.plugins.resource.business.IResource;
import fr.paris.lutece.plugins.resource.business.IResourceType;
import fr.paris.lutece.plugins.resource.business.database.DatabaseResourceHome;
import fr.paris.lutece.plugins.resource.business.database.DatabaseResourceTypeHome;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Provider for database resources
 */
public class DatabaseResourceProvider implements IResourceProvider
{
    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings( "unchecked" )
    public List<IResourceType> getResourceTypeList( )
    {
        return (List<IResourceType>) (List<? extends IResourceType>) DatabaseResourceTypeHome.findAll( );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isResourceTypeManaged( String strResourceTypeName )
    {
        return DatabaseResourceTypeHome.getResourceTypesList( ).contains( strResourceTypeName );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IResource getResource( String strIdResource, String strResourceTypeName )
    {
        if ( StringUtils.isEmpty( strIdResource ) || !StringUtils.isNumeric( strIdResource ) )
        {
            return null;
        }

        return DatabaseResourceHome.findByPrimaryKey( Integer.parseInt( strIdResource ) );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings( "unchecked" )
    public List<IResource> getListResources( String strResourceTypeName )
    {
        return (List<IResource>) (List<? extends IResource>) DatabaseResourceHome.findByResourceType( strResourceTypeName );
    }
}
