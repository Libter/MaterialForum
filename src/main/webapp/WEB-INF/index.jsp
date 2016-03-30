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
                <div class="widget">
                    <h1 class="widget-header">Ostatnio aktywne tematy</h1>
                    <div class="widget-content">
                        <c:forEach var="topic" items="${widgets.lastActiveTopics}">
                            <div class="table widget-row">
                                <div class="table-cell widget-avatar">
                                    <img class="circle" src="${topic.lastPost.user.getAvatar(38)}" />
                                </div>
                                <div class="table-cell widget-text">
                                    <a href="${topic.link}">${topic.getEllipsizedTitle(35)}</a><br />
                                    ${topic.user.formattedNick}
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
                <br />
                <div class="widget">
                    <h1 class="widget-header">Najnowsze tematy</h1>
                    <div class="widget-content">
                        <c:forEach var="topic" items="${widgets.lastTopics}">
                            <div class="table widget-row">
                                <div class="table-cell widget-avatar">
                                    <img class="circle" src="${topic.lastPost.user.getAvatar(38)}" />
                                </div>
                                <div class="table-cell widget-text">
                                    <a href="${topic.link}">${topic.getEllipsizedTitle(35)}</a><br />
                                    ${topic.user.formattedNick}
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>

