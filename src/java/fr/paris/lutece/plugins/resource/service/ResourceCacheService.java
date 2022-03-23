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

import fr.paris.lutece.portal.service.cache.AbstractCacheableService;

/**
 * Cache service for resources, resource types and resources providers
 */
public final class ResourceCacheService extends AbstractCacheableService
{
    private static final String CACHE_SERVICE_NAME = "resource.resourceCacheService";
    private static final String CACHE_KEY_RESOURCE_TYPE_PROVIDER = "resource.provider.resourceType.";
    private static final String CACHE_KEY_RESOURCE_TYPE_LIST = "resource.resourceTypes.list";
    private static final String CACHE_KEY_RESOURCE_DATABASE = "resource.resource.";
    private static final String CACHE_KEY_RESOURCE_TYPE = "resource.resourceType.";
    private static final String CACHE_KEY_RESOURCE = "resource.resource.type.";
    private static final String CONSTANT_POINT = ".";
    private static final ResourceCacheService _instance = new ResourceCacheService( );

    /**
     * Default constructor
     */
    private ResourceCacheService( )
    {
        initCache( );
    }

    /**
     * Get the instance of the service
     * 
     * @return The instance of the service
     */
    public static ResourceCacheService getInstance( )
    {
        return _instance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName( )
    {
        return CACHE_SERVICE_NAME;
    }

    /**
     * Get the cache key of the association between a resource provider and a resource type
     * 
     * @param strResourceType
     *            The resource type
     * @return The cache key of the association between a resource provider and a resource type
     */
    public static String getResourceTypeProviderCacheKey( String strResourceType )
    {
        return CACHE_KEY_RESOURCE_TYPE_PROVIDER + strResourceType;
    }

    /**
     * Get the cache key for the list of resource types
     * 
     * @return The cache key for the list of resource types
     */
    public static String getResourceTypesListCacheKey( )
    {
        return CACHE_KEY_RESOURCE_TYPE_LIST;
    }

    /**
     * Get the cache key for resources
     * 
     * @param strIdResource
     *            The id of the resource
     * @param strResourceType
     *            The resource type
     * @return The cache key for the given resource
     */
    public static String getResourceCacheKey( String strIdResource, String strResourceType )
    {
        StringBuilder sbCacheKey = new StringBuilder( CACHE_KEY_RESOURCE );
        sbCacheKey.append( strResourceType );
        sbCacheKey.append( CONSTANT_POINT );
        sbCacheKey.append( strIdResource );

        return sbCacheKey.toString( );
    }

    /**
     * Get the cache key for resources. This method should only be used to manage database resource type keys. <br />
     * To cache resources of any other providers, use the {@link #getResourceCacheKey(String strIdResource, String strResourceType)} method.
     * 
     * @param strIdResource
     *            The id of the resource
     * @return The cache key for the given resource
     */
    public static String getDatabaseResourceCacheKey( String strIdResource )
    {
        return CACHE_KEY_RESOURCE_DATABASE + strIdResource;
    }

    /**
     * Get the cache for resource types
     * 
     * @param strResourceType
     *            The resource type
     * @return The cache key for the given resource type
     */
    public static String getResourceTypeCacheKey( String strResourceType )
    {
        return CACHE_KEY_RESOURCE_TYPE + strResourceType;
    }
}
