<%@page contentType="text/html" pageEncoding="utf-8"%>
<div class="widget">
    <h1 class="widget-header">Najaktywniejsi użytkownicy</h1>
    <div class="table widget-content">
        <c:forEach var="user" items="${widgets.topPosters}">
            <div class="table-row widget-row">
                <div class="table-cell widget-avatar">
                    <img class="circle" src="${user.getAvatar(38)}" alt="${user.nick}" />
                </div>
                <div class="table-cell widget-user">
                    ${user.formattedNick}
                </div>
                <div class="table-cell widget-right">
                    ${user.postCount}
                </div>
            </div>
        </c:forEach>
    </div>
</div>