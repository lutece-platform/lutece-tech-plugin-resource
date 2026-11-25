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
package fr.paris.lutece.plugins.resource.service.listeners;

import java.util.Locale;

import jakarta.enterprise.inject.Any;
import jakarta.enterprise.inject.spi.CDI;

/**
 * Asks the removal listeners whether a database resource can be removed
 */
public final class DatabaseResourceRemovalManager
{
    /**
     * Default constructor
     */
    private DatabaseResourceRemovalManager( )
    {
        // Private constructor
    }

    /**
     * Check if a database resource can be removed
     *
     * @param strIdResource
     *            The id of the resource
     * @param locale
     *            The locale
     * @return The error message if the resource can not be removed, or null if the resource can be removed
     */
    public static String canResourceBeRemoved( String strIdResource, Locale locale )
    {
        return canResourceBeRemoved( strIdResource, locale, CDI.current( ).select( IDatabaseResourceRemovalListener.class, Any.Literal.INSTANCE ) );
    }

    /**
     * Check if a database resource can be removed, for the given listeners
     *
     * @param strIdResource
     *            The id of the resource
     * @param locale
     *            The locale
     * @param listeners
     *            The removal listeners
     * @return The error message of the first listener that refuses, or null if the resource can be removed
     */
    static String canResourceBeRemoved( String strIdResource, Locale locale, Iterable<? extends IDatabaseResourceRemovalListener> listeners )
    {
        for ( IDatabaseResourceRemovalListener listener : listeners )
        {
            if ( !listener.canBeRemoved( strIdResource ) )
            {
                return listener.getRemovalRefusedMessage( strIdResource, locale );
            }
        }

        return null;
    }
}
