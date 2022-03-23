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
package fr.paris.lutece.plugins.resource.web;

import fr.paris.lutece.plugins.resource.business.database.DatabaseResourceType;
import fr.paris.lutece.plugins.resource.business.database.DatabaseResourceTypeHome;
import fr.paris.lutece.plugins.resource.service.listeners.DatabaseResourceTypeRemovalManager;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.portal.util.mvc.admin.MVCAdminJspBean;
import fr.paris.lutece.portal.util.mvc.admin.annotations.Controller;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.portal.web.util.LocalizedPaginator;
import fr.paris.lutece.util.html.Paginator;
import fr.paris.lutece.util.url.UrlItem;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Jsp Bean to manage resource types
 */
@Controller( controllerJsp = "ManageResourceTypes.jsp", controllerPath = "jsp/admin/plugins/resource/", right = ResourceJspBean.RIGHT_MANAGE_RESOURCES )
public class ResourceTypeJspBean extends MVCAdminJspBean
{
    private static final long serialVersionUID = -5823475836167733365L;

    // Views
    private static final String VIEW_MANAGE_RESOURCE_TYPE = "viewManageResourceType";
    private static final String VIEW_CREATE_RESOURCE_TYPE = "viewCreateResourceType";
    private static final String VIEW_MODIFY_RESOURCE_TYPE = "viewModifyResourceType";
    private static final String VIEW_REMOVE_RESOURCE_TYPE = "viewConfirmRemoveResourceType";

    // Actions
    private static final String ACTION_DO_CREATE_RESOURCE_TYPE = "doCreateResourceType";
    private static final String ACTION_DO_MODIFY_RESOURCE_TYPE = "doModifyResourceType";
    private static final String ACTION_DO_REMOVE_RESOURCE_TYPE = "doRemoveResourceType";

    // Messages
    private static final String MESSAGE_RESOURCE_TYPE_MANAGEMENT_PAGE_TITLE = "resource.resourceTypeManagement.pageTitle";
    private static final String MESSAGE_CREATE_RESOURCE_TYPE_PAGE_TITLE = "resource.createResourceType.pageTitle";
    private static final String MESSAGE_MODIFY_RESOURCE_TYPE_PAGE_TITLE = "resource.modifyResourceType.pageTitle";
    private static final String MESSAGE_RESOURCE_TYPE_CREATE = "resource.createResourceType.resourceTypeCreated";
    private static final String MESSAGE_RESOURCE_TYPE_UPDATED = "resource.modifyResourceType.resourceTypeUpdated";
    private static final String MESSAGE_CONFIRM_REMOVE_RESOURCE_TYPE = "resource.removeResourceType.confirmRemoveResourceType";
    private static final String MESSAGE_UNKNOWN_RESOURCE_TYPE = "resource.removeResourceType.unknownResourceType";
    private static final String MESSAGE_RESOURCE_TYPE_REMOVED = "resource.removeResourceType.resourceTypeRemoved";
    private static final String VALIDATION_ATTRIBUTES_PREFIX = "resource.model.entity.databaseResourceType.attribute.";

    // Marks
    private static final String MARK_LIST_RESOURCE_TYPES = "list_resource_types";
    private static final String MARK_RESOURCE_TYPE = "resource_type";
    private static final String MARK_PAGINATOR = "paginator";
    private static final String MARK_ITEMS_PER_PAGE = "nb_items_per_page";
    private static final String PARAMETER_RESOURCE_TYPE_NAME = "resourceTypeName";

    // Properties
    private static final String PROPERTY_DEFAULT_ITEMS_PER_PAGE = "resource.resourceManagement.defaultItemsPerPage";

    // Templates
    private static final String TEMPLATE_MANAGE_RESOURCE_TYPE = "admin/plugins/resource/manage_resource_types.html";
    private static final String TEMPLATE_CREATE_RESOURCE_TYPE = "admin/plugins/resource/create_resource_type.html";
    private static final String TEMPLATE_MODIFY_RESOURCE_TYPE = "admin/plugins/resource/modify_resource_type.html";
    private int _nDefaultItemsPerPage = AppPropertiesService.getPropertyInt( PROPERTY_DEFAULT_ITEMS_PER_PAGE, 10 );
    private String _strCurrentPageIndex;
    private int _nItemsPerPage;
    private DatabaseResourceType _resourceType;

    /**
     * Get the manage resource type page
     * 
     * @param request
     *            The request
     * @return The HTML content to display
     */
    @View( value = VIEW_MANAGE_RESOURCE_TYPE, defaultView = true )
    public String getManageResourceType( HttpServletRequest request )
    {
        _strCurrentPageIndex = Paginator.getPageIndex( request, Paginator.PARAMETER_PAGE_INDEX, _strCurrentPageIndex );
        _nItemsPerPage = Paginator.getItemsPerPage( request, Paginator.PARAMETER_ITEMS_PER_PAGE, _nItemsPerPage, _nDefaultItemsPerPage );

        Paginator<DatabaseResourceType> paginator = new LocalizedPaginator<>( DatabaseResourceTypeHome.findAll( ), _nItemsPerPage,
                getViewFullUrl( VIEW_MANAGE_RESOURCE_TYPE ), Paginator.PARAMETER_PAGE_INDEX, _strCurrentPageIndex, getLocale( ) );

        _resourceType = null;

        Map<String, Object> model = getModel( );

        model.put( MARK_ITEMS_PER_PAGE, Integer.toString( _nItemsPerPage ) );
        model.put( MARK_PAGINATOR, paginator );
        model.put( MARK_LIST_RESOURCE_TYPES, paginator.getPageItems( ) );

        return getPage( MESSAGE_RESOURCE_TYPE_MANAGEMENT_PAGE_TITLE, TEMPLATE_MANAGE_RESOURCE_TYPE, model );
    }

    /**
     * Get the page to create a resource type
     * 
     * @param request
     *            The request
     * @return The HTML content to display
     */
    @View( value = VIEW_CREATE_RESOURCE_TYPE )
    public String viewCreateResourceType( HttpServletRequest request )
    {
        Map<String, Object> model = getModel( );

        if ( _resourceType != null )
        {
            model.put( MARK_RESOURCE_TYPE, _resourceType );
            _resourceType = null;
        }

        return getPage( MESSAGE_CREATE_RESOURCE_TYPE_PAGE_TITLE, TEMPLATE_CREATE_RESOURCE_TYPE, model );
    }

    /**
     * Do create a resource type
     * 
     * @param request
     *            The request
     * @return the next URL to redirect to
     */
    @Action( ACTION_DO_CREATE_RESOURCE_TYPE )
    public String doCreateResourceType( HttpServletRequest request )
    {
        DatabaseResourceType resourceType = new DatabaseResourceType( );
        populate( resourceType, request );

        if ( !validateBean( resourceType, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            _resourceType = resourceType;

            return redirectView( request, VIEW_CREATE_RESOURCE_TYPE );
        }

        DatabaseResourceTypeHome.insert( resourceType );
        addInfo( MESSAGE_RESOURCE_TYPE_CREATE, getLocale( ) );

        return redirectView( request, VIEW_MANAGE_RESOURCE_TYPE );
    }

    /**
     * Get the page to create a resource type
     * 
     * @param request
     *            The request
     * @return The HTML content to display
     */
    @View( value = VIEW_MODIFY_RESOURCE_TYPE )
    public String viewModifyResourceType( HttpServletRequest request )
    {
        Map<String, Object> model = getModel( );

        DatabaseResourceType resourceType;

        if ( _resourceType != null )
        {
            resourceType = _resourceType;
            _resourceType = null;
        }
        else
        {
            String strResourceType = request.getParameter( PARAMETER_RESOURCE_TYPE_NAME );

            if ( StringUtils.isEmpty( strResourceType ) )
            {
                redirectView( request, VIEW_MANAGE_RESOURCE_TYPE );
            }

            resourceType = DatabaseResourceTypeHome.findByPrimaryKey( strResourceType );
        }

        model.put( MARK_RESOURCE_TYPE, resourceType );

        return getPage( MESSAGE_MODIFY_RESOURCE_TYPE_PAGE_TITLE, TEMPLATE_MODIFY_RESOURCE_TYPE, model );
    }

    /**
     * Do create a resource type
     * 
     * @param request
     *            The request
     * @return the next URL to redirect to
     */
    @Action( ACTION_DO_MODIFY_RESOURCE_TYPE )
    public String doModifyResourceType( HttpServletRequest request )
    {
        DatabaseResourceType resourceType = new DatabaseResourceType( );
        populate( resourceType, request );

        if ( !validateBean( resourceType, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            _resourceType = resourceType;

            return redirectView( request, VIEW_MODIFY_RESOURCE_TYPE );
        }

        DatabaseResourceTypeHome.update( resourceType );
        addInfo( MESSAGE_RESOURCE_TYPE_UPDATED, getLocale( ) );

        return redirectView( request, VIEW_MANAGE_RESOURCE_TYPE );
    }

    /**
     * Get the confirmation message before removing a resource type
     * 
     * @param request
     *            the request
     * @return The next URL to redirect to
     */
    @View( value = VIEW_REMOVE_RESOURCE_TYPE )
    public String getConfirmRemoveResourceType( HttpServletRequest request )
    {
        String strResourceType = request.getParameter( PARAMETER_RESOURCE_TYPE_NAME );

        if ( StringUtils.isEmpty( strResourceType ) )
        {
            addError( MESSAGE_UNKNOWN_RESOURCE_TYPE, getLocale( ) );

            return redirectView( request, VIEW_MANAGE_RESOURCE_TYPE );
        }

        DatabaseResourceType resourceType = DatabaseResourceTypeHome.findByPrimaryKey( strResourceType );

        if ( resourceType == null )
        {
            addError( MESSAGE_UNKNOWN_RESOURCE_TYPE, getLocale( ) );

            return redirectView( request, VIEW_MANAGE_RESOURCE_TYPE );
        }

        UrlItem urlItem = new UrlItem( getActionUrl( ACTION_DO_REMOVE_RESOURCE_TYPE ) );
        urlItem.addParameter( PARAMETER_RESOURCE_TYPE_NAME, strResourceType );

        return redirect( request,
                AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_RESOURCE_TYPE, urlItem.getUrl( ), AdminMessage.TYPE_CONFIRMATION ) );
    }

    /**
     * Do remove a resource type
     * 
     * @param request
     *            The request
     * @return The next URL to redirect to
     */
    @Action( value = ACTION_DO_REMOVE_RESOURCE_TYPE )
    public String doRemoveResourceType( HttpServletRequest request )
    {
        String strResourceType = request.getParameter( PARAMETER_RESOURCE_TYPE_NAME );

        if ( StringUtils.isEmpty( strResourceType ) )
        {
            addError( MESSAGE_UNKNOWN_RESOURCE_TYPE, getLocale( ) );
        }
        else
        {
            DatabaseResourceType resourceType = DatabaseResourceTypeHome.findByPrimaryKey( strResourceType );

            if ( resourceType == null )
            {
                addError( MESSAGE_UNKNOWN_RESOURCE_TYPE, getLocale( ) );
            }
            else
            {
                String strError = DatabaseResourceTypeRemovalManager.canResourceTypeBeRemoved( strResourceType, getLocale( ) );

                if ( strError != null )
                {
                    addError( strError );
                }
                else
                {
                    DatabaseResourceTypeHome.delete( strResourceType );
                    addInfo( MESSAGE_RESOURCE_TYPE_REMOVED, getLocale( ) );
                }
            }
        }

        return redirectView( request, VIEW_MANAGE_RESOURCE_TYPE );
    }
}
