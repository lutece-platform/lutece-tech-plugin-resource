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

import fr.paris.lutece.plugins.resource.business.IResourceType;
import fr.paris.lutece.portal.service.util.AppLogService;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


/**
 * Resource type
 */
public class DatabaseResourceType implements IResourceType, Serializable, Cloneable
{
    private static final long serialVersionUID = -3153534135959473655L;
    @NotEmpty( message = "#i18n{resource.model.entity.databaseResourceType.attribute.resourceTypeName.notEmpty}" )
    @Size( max = 255, message = "#i18n{resource.model.entity.databaseResourceType.attribute.resourceTypeName.size}" )
    @Pattern( regexp = "[a-zA-Z0-9]*", message = "#i18n{resource.model.entity.databaseResourceType.attribute.resourceTypeName.pattern}" )
    private String _strResourceTypeName;
    @NotEmpty( message = "#i18n{resource.model.entity.databaseResourceType.attribute.resourceTypeDescription.notEmpty}" )
    @Size( max = 255, message = "#i18n{resource.model.entity.databaseResourceType.attribute.resourceTypeDescription.size}" )
    private String _strResourceTypeDescription;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getResourceTypeName(  )
    {
        return _strResourceTypeName;
    }

    /**
     * Set the name of the resource type
     * @param strResourceTypeName The name of the resource type
     */
    public void setResourceTypeName( String strResourceTypeName )
    {
        this._strResourceTypeName = strResourceTypeName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getResourceTypeDescription(  )
    {
        return _strResourceTypeDescription;
    }

    /**
     * Set the description of the resource type
     * @param strResourceTypeDescription The description of the resource type
     */
    public void setResourceTypeDescription( String strResourceTypeDescription )
    {
        this._strResourceTypeDescription = strResourceTypeDescription;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatabaseResourceType clone(  )
    {
        try
        {
            return (DatabaseResourceType) super.clone(  );
        }
        catch ( CloneNotSupportedException e )
        {
            AppLogService.error( e.getMessage(  ), e );
        }

        return null;
    }
}
