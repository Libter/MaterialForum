<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
    <%@ include file="/WEB-INF/include/head.jsp" %>
    <body>
        <%@ include file="/WEB-INF/include/menu.jsp" %>
        <br />
        <div id="forums">
            <c:forEach var="forum" items="${forums}">
                <c:if test="${empty forum.parent}">
                    <ul class="collection with-header">
                        <li class="collection-header"><h5><a href="/forum/${forum.urlName}">${forum.displayName}</a></h5></li>
                        
                        <c:forEach var="subforum" items="${forums}">
                            <c:if test="${forum.name.equals(subforum.parent.name)}">
                                <li class="collection-item"><a href="/forum/${subforum.urlName}">${subforum.displayName}</a></li>
                            </c:if>
                        </c:forEach>
                    </ul>
                </c:if>
            </c:forEach> 
        </div>
    </body>
</html>

