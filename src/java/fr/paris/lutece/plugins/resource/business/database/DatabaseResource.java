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

import fr.paris.lutece.plugins.resource.business.IResource;
import fr.paris.lutece.portal.service.util.AppLogService;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;


/**
 * Business class for resources
 */
public class DatabaseResource implements IResource, Cloneable
{
    private static final long serialVersionUID = -6889675846023730084L;
    private String _strIdResource;
    @NotEmpty( message = "#i18n{resource.model.entity.databaseResource.attribute.resourceType.notEmpty}" )
    @Size( max = 255, min = 1, message = "#i18n{resource.model.entity.databaseResource.attribute.resourceType.size}" )
    private String _strResourceType;
    @NotEmpty( message = "#i18n{resource.model.entity.databaseResource.attribute.resourceName.notEmpty}" )
    @Size( max = 255, min = 1, message = "#i18n{resource.model.entity.databaseResource.attribute.resourceName.size}" )
    private String _strResourceName;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getIdResource(  )
    {
        return _strIdResource;
    }

    /**
     * Set the id of the resource
     * @param nIdResource The id of the resource
     */
    protected void setIdResource( int nIdResource )
    {
        this._strIdResource = Integer.toString( nIdResource );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getResourceType(  )
    {
        return _strResourceType;
    }

    /**
     * Set the type of this resource
     * @param strResourceType The type of this resource
     */
    public void setResourceType( String strResourceType )
    {
        this._strResourceType = strResourceType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getResourceName(  )
    {
        return _strResourceName;
    }

    /**
     * Set the name of this resource
     * @param strResourceName The name of this resource
     */
    public void setResourceName( String strResourceName )
    {
        this._strResourceName = strResourceName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatabaseResource clone(  )
    {
        try
        {
            return (DatabaseResource) super.clone(  );
        }
        catch ( CloneNotSupportedException e )
        {
            AppLogService.error( e.getMessage(  ), e );
        }

        return null;
    }
}
