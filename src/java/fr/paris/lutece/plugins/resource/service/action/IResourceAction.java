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
package fr.paris.lutece.plugins.resource.service.action;

import java.util.Locale;


/**
 * Interface for action to apply on resources <br />
 * IResourceAction only represents action button displayed. The content of the
 * action should be implemented by a controller pointed by the URL of the
 * action.
 */
public interface IResourceAction
{
    /**
     * Check if the action can be performed hover a given resource
     * @param strIdResource The id of the resource
     * @param strResourceType The resource type
     * @return True if the action can be performed, false otherwise
     */
    boolean canActionBePerformed( String strIdResource, String strResourceType );

    /**
     * Get the URL of the action to perform
     * @param strIdResource The id of the resource
     * @param strResourceType The resource type
     * @return The URL of the action
     */
    String getActionUrl( String strIdResource, String strResourceType );

    /**
     * Get the CSS class to display the icon of the action
     * @param strIdResource The id of the resource
     * @param strResourceType The resource type
     * @return the CSS class to display the icon of the action
     */
    String getIcon( String strIdResource, String strResourceType );

    /**
     * Get the title of the action
     * @param strIdResource The id of the resource
     * @param strResourceType The resource type
     * @param locale The locale
     * @return The title of the action
     */
    String getTitle( String strIdResource, String strResourceType, Locale locale );
}
