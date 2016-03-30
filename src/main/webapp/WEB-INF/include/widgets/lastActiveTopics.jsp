<%@page contentType="text/html" pageEncoding="utf-8"%>
<div class="widget">
    <h1 class="widget-header">Ostatnio aktywne tematy</h1>
    <div class="widget-content">
        <c:forEach var="topic" items="${widgets.lastActiveTopics}">
            <div class="table widget-row">
                <div class="table-cell widget-avatar">
                    <img class="circle" src="${topic.lastPost.user.getAvatar(38)}" />
                </div>
                <div class="table-cell widget-topic">
                    <a href="${topic.link}">${topic.getEllipsizedTitle(35)}</a><br />
                    ${topic.user.formattedNick}
                </div>
            </div>
        </c:forEach>
    </div>
</div>