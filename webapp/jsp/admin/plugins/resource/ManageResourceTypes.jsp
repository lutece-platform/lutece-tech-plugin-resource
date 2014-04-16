<jsp:useBean id="manageResourceTypes" scope="session" class="fr.paris.lutece.plugins.resource.web.ResourceTypeJspBean" />
<% String strContent = manageResourceTypes.processController ( request , response ); %>

<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<%= strContent %>

<%@ include file="../../AdminFooter.jsp" %>
