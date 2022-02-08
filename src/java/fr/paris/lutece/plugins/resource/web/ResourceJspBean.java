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
package fr.paris.lutece.plugins.resource.web;

import fr.paris.lutece.plugins.resource.business.database.DatabaseResource;
import fr.paris.lutece.plugins.resource.business.database.DatabaseResourceHome;
import fr.paris.lutece.plugins.resource.business.database.DatabaseResourceSort;
import fr.paris.lutece.plugins.resource.business.database.DatabaseResourceType;
import fr.paris.lutece.plugins.resource.business.database.DatabaseResourceTypeHome;
import fr.paris.lutece.plugins.resource.service.action.IResourceAction;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.portal.util.mvc.admin.MVCAdminJspBean;
import fr.paris.lutece.portal.util.mvc.admin.annotations.Controller;
import fr.paris.lutece.portal.util.mvc.commons.annotations.Action;
import fr.paris.lutece.portal.util.mvc.commons.annotations.View;
import fr.paris.lutece.portal.web.constants.Parameters;
import fr.paris.lutece.portal.web.util.LocalizedDelegatePaginator;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.html.DelegatePaginator;
import fr.paris.lutece.util.html.Paginator;
import fr.paris.lutece.util.url.UrlItem;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


/**
 * Jsp Bean to manage resources
 */
@Controller( controllerJsp = "ManageResources.jsp", controllerPath = "jsp/admin/plugins/resource/", right = ResourceJspBean.RIGHT_MANAGE_RESOURCES )
public class ResourceJspBean extends MVCAdminJspBean
{
    /**
     * Right to manage resources
     */
    public static final String RIGHT_MANAGE_RESOURCES = "RESOURCE_MANAGE_RESOURCES";
    private static final long serialVersionUID = -7527847344523994706L;

    // Views
    private static final String VIEW_MANAGE_RESOURCES = "viewManageResources";
    private static final String VIEW_CREATE_RESOURCE = "viewCreateResource";
    private static final String VIEW_MODIFY_RESOURCE = "viewModifyResource";
    private static final String VIEW_CONFIRM_REMOVE_RESOURCE = "viewConfirmRemoveResource";

    // Actions
    private static final String ACTION_DO_CREATE_RESOURCE = "doCreateResource";
    private static final String ACTION_DO_REMOVE_RESOURCE = "doRemoveResource";
    private static final String ACTION_DO_MODIFY_RESOURCE = "doModifyResource";

    // Messages
    private static final String MESSAGE_RESOURCE_MANAGEMENT_PAGE_TITLE = "resource.resourceManagement.pageTitle";
    private static final String MESSAGE_CREATE_RESOURCE_PAGE_TITLE = "resource.createResource.pageTitle";
    private static final String MESSAGE_MODIFY_RESOURCE_PAGE_TITLE = "resource.modifyResource.pageTitle";
    private static final String MESSAGE_RESOURCE_CREATED = "resource.createResource.messageResourceCreated";
    private static final String MESSAGE_CONFIRM_REMOVE_RESOURCE = "resource.removeResource.confirmationMessage";
    private static final String MESSAGE_RESOURCE_REMOVED = "resource.removeResource.resourceRemoved";
    private static final String MESSAGE_RESOURCE_MODIFIED = "resource.modifyResource.resourceModified";

    // Marks
    private static final String MARK_LIST_RESOURCES = "listResources";
    private static final String MARK_REFERENCE_LIST_RESOURCE_TYPES = "refListResourceTypes";
    private static final String MARK_RESOURCE_TYPE = "resource_type";
    private static final String MARK_RESOURCE = "resource";
    private static final String MARK_PAGINATOR = "paginator";
    private static final String MARK_ITEMS_PER_PAGE = "nb_items_per_page";
    private static final String MARK_LIST_ACTIONS = "list_actions";
    private static final String MARK_LOCALE = "locale";

    // Parameters
    private static final String PARAMETER_ID_RESOURCE = "idResource";

    // Properties
    private static final String PROPERTY_DEFAULT_ITEMS_PER_PAGE = "resource.resourceManagement.defaultItemsPerPage";
    private static final String VALIDATION_ATTRIBUTES_PREFIX = "resource.model.entity.databaseResource.attribute.";

    // Templates
    private static final String TEMPLATE_MANAGE_RESOURCES = "admin/plugins/resource/manage_resources.html";
    private static final String TEMPLATE_CREATE_RESOURCE = "admin/plugins/resource/create_resource.html";
    private static final String TEMPLATE_MODIFY_RESOURCE = "admin/plugins/resource/modify_resource.html";
    private String _strSort;
    private boolean _bSortAsc;
    private int _nDefaultItemsPerPage = AppPropertiesService.getPropertyInt( PROPERTY_DEFAULT_ITEMS_PER_PAGE, 10 );
    private String _strCurrentPageIndex;
    private int _nItemsPerPage;
    private DatabaseResource _resource;

    /**
     * Get the page to display the list of database resources
     * @param request The request
     * @return The HTML content to display
     */
    @View( value = VIEW_MANAGE_RESOURCES, defaultView = true )
    public String getManageResource( HttpServletRequest request )
    {
        _resource = null;

        // We update the sort parameters
        String strSort = request.getParameter( Parameters.SORTED_ATTRIBUTE_NAME );
        String strSortAsc = request.getParameter( Parameters.SORTED_ASC );

        if ( StringUtils.isNotEmpty( strSort ) )
        {
            _strSort = strSort;
        }

        if ( StringUtils.isNotEmpty( strSortAsc ) )
        {
            _bSortAsc = Boolean.parseBoolean( strSortAsc );
        }

        // We update the pagination parameters
        _strCurrentPageIndex = Paginator.getPageIndex( request, Paginator.PARAMETER_PAGE_INDEX, _strCurrentPageIndex );
        _nItemsPerPage = Paginator.getItemsPerPage( request, Paginator.PARAMETER_ITEMS_PER_PAGE, _nItemsPerPage,
                _nDefaultItemsPerPage );

        DatabaseResourceSort resourceSort = DatabaseResourceSort.getDatabaseResourceSort( _strSort, _bSortAsc );

        // We get the items to display
        List<Integer> listResourcesId = DatabaseResourceHome.findAllId( resourceSort );

        String strViewUrl = getViewFullUrl( VIEW_MANAGE_RESOURCES );

        Paginator<Integer> paginatorIds = new Paginator<>( listResourcesId, _nItemsPerPage, strViewUrl,
                Paginator.PARAMETER_PAGE_INDEX, _strCurrentPageIndex );

        DelegatePaginator<DatabaseResource> paginatorItems = new LocalizedDelegatePaginator<>( DatabaseResourceHome.findByListId( 
                    paginatorIds.getPageItems(  ), resourceSort ), _nItemsPerPage, strViewUrl,
                Paginator.PARAMETER_PAGE_INDEX, _strCurrentPageIndex, paginatorIds.getItemsCount(  ), getLocale(  ) );

        // We get the reference list of resource types
        ReferenceList refListItems = new ReferenceList(  );

        for ( DatabaseResourceType resourceType : DatabaseResourceTypeHome.findAll(  ) )
        {
            refListItems.addItem( resourceType.getResourceTypeName(  ), resourceType.getResourceTypeDescription(  ) );
        }

        Map<String, Object> model = new HashMap<>(  );

        model.put( MARK_ITEMS_PER_PAGE, Integer.toString( _nItemsPerPage ) );
        model.put( MARK_PAGINATOR, paginatorItems );
        model.put( MARK_LIST_RESOURCES, paginatorItems.getPageItems(  ) );
        model.put( MARK_REFERENCE_LIST_RESOURCE_TYPES, refListItems );
        model.put( MARK_LIST_ACTIONS, SpringContextService.getBeansOfType( IResourceAction.class ) );
        model.put( MARK_LOCALE, getLocale(  ) );
        fillCommons( model );

        return getPage( MESSAGE_RESOURCE_MANAGEMENT_PAGE_TITLE, TEMPLATE_MANAGE_RESOURCES, model );
    }

    /**
     * Get the page to create a resource
     * @param request The request
     * @return The HTML content to display
     */
    @View( value = VIEW_CREATE_RESOURCE )
    public String getCreateResource( HttpServletRequest request )
    {
        String strResourceType = ( _resource != null ) ? _resource.getResourceType(  )
                                                       : request.getParameter( MARK_RESOURCE_TYPE );

        if ( StringUtils.isEmpty( strResourceType ) )
        {
            return redirectView( request, VIEW_MANAGE_RESOURCES );
        }

        DatabaseResourceType databaseResourceType = DatabaseResourceTypeHome.findByPrimaryKey( strResourceType );

        Map<String, Object> model = new HashMap<>(  );
        model.put( MARK_RESOURCE_TYPE, databaseResourceType );
        model.put( MARK_RESOURCE, _resource );
        fillCommons( model );
        _resource = null;

        return getPage( MESSAGE_CREATE_RESOURCE_PAGE_TITLE, TEMPLATE_CREATE_RESOURCE, model );
    }

    /**
     * Do create a resource
     * @param request The request
     * @return True
     */
    @Action( value = ACTION_DO_CREATE_RESOURCE )
    public String doCreateResource( HttpServletRequest request )
    {
        DatabaseResource resource = new DatabaseResource(  );

        populate( resource, request );

        if ( !validateBean( resource, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            _resource = resource;
            redirectView( request, VIEW_CREATE_RESOURCE );
        }

        DatabaseResourceHome.create( resource );
        _resource = null;

        addInfo( MESSAGE_RESOURCE_CREATED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_RESOURCES );
    }

    /**
     * Get the page to create a resource
     * @param request The request
     * @return The HTML content to display
     */
    @View( value = VIEW_MODIFY_RESOURCE )
    public String getModifyResource( HttpServletRequest request )
    {
        String strIdResource = ( _resource != null ) ? _resource.getIdResource(  )
                                                     : request.getParameter( PARAMETER_ID_RESOURCE );

        if ( StringUtils.isEmpty( strIdResource ) || !StringUtils.isNumeric( strIdResource ) )
        {
            return redirectView( request, VIEW_MANAGE_RESOURCES );
        }

        DatabaseResource databaseResource = ( _resource != null ) ? _resource
                                                                  : DatabaseResourceHome.findByPrimaryKey( Integer.parseInt( 
                    strIdResource ) );
        _resource = null;

        Map<String, Object> model = new HashMap<>(  );
        model.put( MARK_RESOURCE, databaseResource );
        model.put( MARK_RESOURCE_TYPE, DatabaseResourceTypeHome.findByPrimaryKey( databaseResource.getResourceType(  ) ) );
        fillCommons( model );

        return getPage( MESSAGE_MODIFY_RESOURCE_PAGE_TITLE, TEMPLATE_MODIFY_RESOURCE, model );
    }

    /**
     * Do modify a resource
     * @param request The request
     * @return The HTML content to display
     */
    @Action( value = ACTION_DO_MODIFY_RESOURCE )
    public String doModifyResource( HttpServletRequest request )
    {
        String strIdResource = ( _resource != null ) ? _resource.getIdResource(  )
                                                     : request.getParameter( PARAMETER_ID_RESOURCE );

        if ( StringUtils.isEmpty( strIdResource ) || !StringUtils.isNumeric( strIdResource ) )
        {
            return redirectView( request, VIEW_MANAGE_RESOURCES );
        }

        DatabaseResource resource = DatabaseResourceHome.findByPrimaryKey( Integer.parseInt( strIdResource ) );

        populate( resource, request );

        if ( !validateBean( resource, VALIDATION_ATTRIBUTES_PREFIX ) )
        {
            _resource = resource;
            redirectView( request, VIEW_MODIFY_RESOURCE );
        }

        DatabaseResourceHome.update( resource );
        _resource = null;

        addInfo( MESSAGE_RESOURCE_MODIFIED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_RESOURCES );
    }

    /**
     * Confirm the removal of a resource
     * @param request the request
     * @return The HTML content to display
     */
    @View( value = VIEW_CONFIRM_REMOVE_RESOURCE )
    public String getConfirmRemoveResource( HttpServletRequest request )
    {
        String strIdResource = request.getParameter( PARAMETER_ID_RESOURCE );

        if ( StringUtils.isEmpty( strIdResource ) || !StringUtils.isNumeric( strIdResource ) )
        {
            redirectView( request, VIEW_MANAGE_RESOURCES );
        }

        UrlItem urlItem = new UrlItem( getActionUrl( ACTION_DO_REMOVE_RESOURCE ) );
        urlItem.addParameter( PARAMETER_ID_RESOURCE, strIdResource );

        return redirect( request,
            AdminMessageService.getMessageUrl( request, MESSAGE_CONFIRM_REMOVE_RESOURCE, urlItem.getUrl(  ),
                AdminMessage.TYPE_CONFIRMATION ) );
    }

    /**
     * Do remove a resource
     * @param request The request
     * @return the next URL to redirect to
     */
    @Action( value = ACTION_DO_REMOVE_RESOURCE )
    public String doRemoveResource( HttpServletRequest request )
    {
        String strIdResource = request.getParameter( PARAMETER_ID_RESOURCE );

        if ( StringUtils.isEmpty( strIdResource ) || !StringUtils.isNumeric( strIdResource ) )
        {
            redirectView( request, VIEW_MANAGE_RESOURCES );
        }

        int nIdResource = Integer.parseInt( strIdResource );
        DatabaseResourceHome.delete( nIdResource );

        addInfo( MESSAGE_RESOURCE_REMOVED, getLocale(  ) );

        return redirectView( request, VIEW_MANAGE_RESOURCES );
    }
}
