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
package fr.paris.lutece.plugins.resource.business;


/**
 * Default implementation of resource types
 */
public class ResourceTypeDefaultImplementation implements IResourceType
{
    private final String _strResourceTypeName;
    private final String _strResourceTypeDescription;

    /**
     * Creates a new resource type
     * @param strResourceTypeName The name of the resource type. The name is
     *            used as primary key and is not displayed to users.
     * @param strResourceTypeDescription The description of the resource type.
     *            The description is used to be displayed to users.
     */
    public ResourceTypeDefaultImplementation( String strResourceTypeName, String strResourceTypeDescription )
    {
        this._strResourceTypeName = strResourceTypeName;
        this._strResourceTypeDescription = strResourceTypeDescription;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getResourceTypeName(  )
    {
        return _strResourceTypeName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getResourceTypeDescription(  )
    {
        return _strResourceTypeDescription;
    }
}
