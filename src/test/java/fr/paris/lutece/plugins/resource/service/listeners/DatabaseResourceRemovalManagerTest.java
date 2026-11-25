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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.Test;

/**
 * A database resource still referenced elsewhere (an appointment it is assigned to) is not removed.
 */
public class DatabaseResourceRemovalManagerTest
{
    private static final IDatabaseResourceRemovalListener ASSIGNED_42 = new IDatabaseResourceRemovalListener( )
    {
        @Override
        public boolean canBeRemoved( String strId )
        {
            return !"42".equals( strId );
        }

        @Override
        public String getRemovalRefusedMessage( String strId, Locale locale )
        {
            return "resource " + strId + " is assigned";
        }
    };

    @Test
    public void testAssignedResourceIsNotRemoved( )
    {
        assertEquals( "resource 42 is assigned", DatabaseResourceRemovalManager.canResourceBeRemoved( "42", Locale.FRENCH, List.of( ASSIGNED_42 ) ) );
    }

    @Test
    public void testFreeResourceIsRemoved( )
    {
        assertNull( DatabaseResourceRemovalManager.canResourceBeRemoved( "43", Locale.FRENCH, List.of( ASSIGNED_42 ) ) );
    }
}
