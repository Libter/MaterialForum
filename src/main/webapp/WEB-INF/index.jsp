<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/include/head.jsp" %>
    </head>
    <body>
        <%@ include file="/WEB-INF/include/menu.jsp" %>
        <br />
        <div id="main">
            <c:forEach var="forum" items="${forums}">
                <c:if test="${empty forum.parent}">
                    <%@ include file="/WEB-INF/include/forumlist.jsp" %>
                </c:if>
            </c:forEach> 
        </div>
    </body>
</html>

