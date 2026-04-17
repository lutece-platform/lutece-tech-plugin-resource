<%@ page errorPage="../../ErrorPage.jsp" %>

${ pageContext.setAttribute( 'strContent', resourceJspBean.processController( pageContext.request , pageContext.response ) ) }

<jsp:include page="../../AdminHeader.jsp" />

${ pageContext.getAttribute( 'strContent' ) }

<%@ include file="../../AdminFooter.jsp" %>
