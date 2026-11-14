/*
 * Copyright (c) 2002-2026, City of Paris
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
package fr.paris.lutece.plugins.resource.web;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

/**
 * The right of the management screens is the one the plugin descriptor declares and the one the SQL script grants, so
 * that the feature is listed in the administration menu.
 */
public class ResourceRightTest
{
    private static final String PLUGIN_DESCRIPTOR = "webapp/WEB-INF/plugins/resource.xml";
    private static final String SQL_INIT_CORE = "src/sql/plugins/resource/core/init_core_resource.sql";

    @Test
    public void testDescriptorAndSqlDeclareTheRightOfTheControllers( ) throws IOException
    {
        String strDescriptor = Files.readString( Path.of( PLUGIN_DESCRIPTOR ), StandardCharsets.UTF_8 );
        String strSql = Files.readString( Path.of( SQL_INIT_CORE ), StandardCharsets.UTF_8 );

        assertTrue( strDescriptor.contains( "<feature-id>" + ResourceJspBean.RIGHT_MANAGE_RESOURCES + "</feature-id>" ), strDescriptor );
        assertTrue( strSql.contains( "core_admin_right" ) && strSql.contains( "'" + ResourceJspBean.RIGHT_MANAGE_RESOURCES + "'" ), strSql );
    }
}
