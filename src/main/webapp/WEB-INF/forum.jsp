<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <%@ include file="/WEB-INF/include/forumlist.jsp" %>
            <ul class="collection">
                <c:forEach var="topic" items="${topics}">
                    <li class="collection-item"><a href="${topic.link}">${topic.title}</a></li>
                </c:forEach>  
            </ul>
        </div>
        <c:if test="${!empty sessionScope.user}">
            <div class="fixed-action-btn">
                <a href="${forum.addLink}" class="btn-floating btn-large waves-effect waves-light red"><i class="material-icons">add</i></a>
            </div>
        </c:if>
    </body>
</html>
