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

import java.util.List;

/**
 * Interface for resource providers. Providers must be declared as Spring beans.
 */
public interface IResourceProvider
{
    /**
     * Get the list of resource types managed by this provider
     * 
     * @return The list of resource types managed by this provider
     */
    List<IResourceType> getResourceTypeList( );

    /**
     * Check if a resource type is managed by this provider
     * 
     * @param strResourceTypeName
     *            The resource type
     * @return True if the resource type is managed by this provider, false otherwise
     */
    boolean isResourceTypeManaged( String strResourceTypeName );

    /**
     * Get a resource. The resources is assured to be associated with this provider.
     * 
     * @param strIdResource
     *            The id of the resource
     * @param strResourceTypeName
     *            the resource type
     * @return The resource, or null if no resource is associated with the given resource id and resource type for this provider
     */
    IResource getResource( String strIdResource, String strResourceTypeName );

    /**
     * Get the list of resources managed by this provider that have a given resource type
     * 
     * @param strResourceTypeName
     *            The resource type
     * @return The list of resources, or an empty list if no resource is associated with the resource type
     */
    List<IResource> getListResources( String strResourceTypeName );
}
