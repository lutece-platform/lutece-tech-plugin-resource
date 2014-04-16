<jsp:useBean id="manageResources" scope="session" class="fr.paris.lutece.plugins.resource.web.ResourceJspBean" />
<% String strContent = manageResources.processController ( request , response ); %>

<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<%= strContent %>

<%@ include file="../../AdminFooter.jsp" %>
