<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
    <%@include file="/WEB-INF/include/head.jsp" %>
    <body>
        <%@ include file="/WEB-INF/include/menu.jsp" %>
        <div id="forums">
            <ul class="collection with-header">
                <li class="collection-header"><h5><a href="/forum/${forum.urlName}">${forum.displayName}</a></h5></li>
                <c:forEach var="subforum" items="${forums}">
                    <c:if test="${forum.name.equals(subforum.parent.name)}">
                        <li class="collection-item"><a href="/forum/${subforum.urlName}">${subforum.displayName}</a></li>
                    </c:if>
                </c:forEach>
            </ul>
        </div>
        <br />
    </body>
</html>
