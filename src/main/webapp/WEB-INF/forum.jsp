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
            <br />
            <c:if test="${!empty forum.parent}">
                <h1 class="panel-header">Tematy</h1>
                <div class="panel-content">
                    <c:forEach var="topic" items="${topics}" varStatus="topicStatus">
                        <div class="panel-entry ${topicStatus.index % 2 == 0 ? 'even' : 'odd'}">
                            <div>
                                <h1><a href="${topic.link}">${topic.title}</a></h1>
                            </div>
                            <div class="count">
                                ${topic.postCount} postów
                            </div>
                            <div class="avatar">
                                <img src="${topic.lastPost.user.avatar}" />
                            </div>
                            <div class="lastpost">
                                ${topic.lastPost.user.nick}<br />
                                ${topic.lastPost.formattedCreationDate}
                            </div>
                        </div>
                    </c:forEach>  
                </div>
            </c:if>
        </div>
        <c:if test="${!empty sessionScope.user && !empty forum.parent}">
            <div class="fixed-action-btn">
                <a href="${forum.addLink}" class="btn-floating btn-large waves-effect waves-light red"><i class="material-icons">add</i></a>
            </div>
        </c:if>
    </body>
</html>
