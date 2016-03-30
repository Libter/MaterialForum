<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
    <head>
        <%@ include file="/WEB-INF/include/head.jsp" %>
    </head>
    <body>
        <%@ include file="/WEB-INF/include/menu.jsp" %>
        <br />
        <div id="main" class="table">
            <div class="table-cell">
                <c:forEach var="forum" items="${forums}">
                    <c:if test="${empty forum.parent && forum.canRead(user)}">
                        <%@ include file="/WEB-INF/include/forumlist.jsp" %>
                        <br />
                    </c:if>
                </c:forEach> 
            </div>
            <div class="table-cell sidebar">
                <%@ include file="/WEB-INF/include/widgets/lastActiveTopics.jsp" %>
                <br />
                <%@ include file="/WEB-INF/include/widgets/lastTopics.jsp" %>
                <br />
                <%@ include file="/WEB-INF/include/widgets/topPosters.jsp" %>
            </div>
        </div>
    </body>
</html>

