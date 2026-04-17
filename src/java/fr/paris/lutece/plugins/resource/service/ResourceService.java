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
package fr.paris.lutece.plugins.resource.service;

import fr.paris.lutece.plugins.resource.business.IResource;
import fr.paris.lutece.plugins.resource.business.IResourceType;
import fr.paris.lutece.plugins.resource.service.provider.IResourceProvider;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Any;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;

/**
 * Resource service
 */
@ApplicationScoped
public class ResourceService
{
    @Inject
    private ResourceCacheService _cacheService;

    @Inject
    @Any
    private Instance<IResourceProvider> _providers;

    /**
     * Get the list of available resource types
     *
     * @return The list of available resource types
     */
    @SuppressWarnings( "unchecked" )
    public List<IResourceType> getResourceTypesList( )
    {
        String strCacheKey = ResourceCacheService.getResourceTypesListCacheKey( );
        List<IResourceType> listResourceTypes = (List<IResourceType>) _cacheService.get( strCacheKey );

        if ( listResourceTypes != null )
        {
            return listResourceTypes;
        }

        listResourceTypes = new ArrayList<>( );

        for ( IResourceProvider provider : _providers )
        {
            listResourceTypes.addAll( provider.getResourceTypeList( ) );
        }

        _cacheService.put( strCacheKey, listResourceTypes );

        return listResourceTypes;
    }

    /**
     * Check if a resource type is managed by any provider
     *
     * @param strResourceTypeName
     *            The resource type
     * @return True if the resource type is managed by any provider, false otherwise
     */
    public boolean isResourceTypeManaged( String strResourceTypeName )
    {
        return getResourceProvider( strResourceTypeName ) != null;
    }

    /**
     * Declare a resource type as created. This method is used to keep cache up to date.
     *
     * @param strResourceTypeName
     *            The created resource type
     */
    public void resourceTypeCreated( String strResourceTypeName )
    {
        _cacheService.remove( ResourceCacheService.getResourceTypesListCacheKey( ) );
    }

    /**
     * Declare a resource type as removed. This method is used to keep cache up to date.
     *
     * @param strResourceTypeName
     *            The removed resource type
     */
    public void resourceTypeRemoved( String strResourceTypeName )
    {
        _cacheService.remove( ResourceCacheService.getResourceTypeProviderCacheKey( strResourceTypeName ) );
        _cacheService.remove( ResourceCacheService.getResourceTypesListCacheKey( ) );
    }

    /**
     * Get a resource from its id and type
     *
     * @param strIdResource
     *            the id of the resource to get
     * @param strResourceTypeName
     *            the type of the resource to get
     * @return The resource, or null if the resource could not be found
     */
    public IResource getResource( String strIdResource, String strResourceTypeName )
    {
        IResourceProvider provider = getResourceProvider( strResourceTypeName );

        if ( provider != null )
        {
            return provider.getResource( strIdResource, strResourceTypeName );
        }

        return null;
    }

    /**
     * Get the list of resources of a given type
     *
     * @param strResourceTypeName
     *            the resource type
     * @return the list of resource of the given type, or an empty list if not resource was found
     */
    public List<IResource> getListResources( String strResourceTypeName )
    {
        IResourceProvider resourceProvider = getResourceProvider( strResourceTypeName );

        if ( resourceProvider != null )
        {
            return resourceProvider.getListResources( strResourceTypeName );
        }

        return new ArrayList<>( 0 );
    }

    /**
     * Get the resource provider of a resource type
     *
     * @param strResourceTypeName
     *            The resource provider of a resource type
     * @return The resource provider, or null if no provider was found for the given resource type
     */
    public IResourceProvider getResourceProvider( String strResourceTypeName )
    {
        // CDI providers are already @ApplicationScoped singletons, no need to cache them
        // (CDI proxies cannot be serialized by EHCache)
        for ( IResourceProvider resourceProvider : _providers )
        {
            if ( resourceProvider.isResourceTypeManaged( strResourceTypeName ) )
            {
                return resourceProvider;
            }
        }

        return null;
    }
}
