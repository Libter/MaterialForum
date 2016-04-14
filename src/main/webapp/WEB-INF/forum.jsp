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
            <%@ include file="/WEB-INF/include/forumlist.jsp" %>
            <br />
            <c:if test="${!empty forum.parent}">
                <h1 class="forum-header">Tematy</h1>
                <div class="table forum-content">
                    <c:forEach var="topic" items="${topics}" varStatus="topicStatus">
                        <div class="table-row forum-row ${topicStatus.index % 2 == 0 ? 'even' : 'odd'}">
                            <div class="table-cell topic">
                                <h1>
                                    <c:if test="${topic.isClosed()}"><i class="fa fa-lock"></i></c:if>
                                    <a href="${topic.link}">${topic.title}</a>
                                </h1>
                            </div>
                            <div class="table-cell count">
                                ${topic.formattedPostCount}
                            </div>
                            <div class="table-cell avatar">
                                <img class="circle" src="${topic.lastPost.user.mediumAvatar}" />
                            </div>
                            <div class="table-cell lastpost">
                                ${topic.lastPost.user.formattedNick}<br />
                                ${topic.lastPost.formattedCreationDate}
                            </div>
                        </div>
                    </c:forEach>  
                </div>
            </c:if>
        </div>
        <c:if test="${!empty forum.parent && forum.canWriteTopics(user)}">
            <div class="fixed-action-btn">
                <a href="${forum.addLink}" class="btn-floating btn-large waves-effect waves-light red"><i class="material-icons">add</i></a>
            </div>
        </c:if>
    </body>
</html>
