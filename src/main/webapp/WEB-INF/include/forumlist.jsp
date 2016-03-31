<%@page contentType="text/html" pageEncoding="utf-8" %>

<c:set var="subforums" value="${forum.getChildren(user)}" />
<c:if test="${!empty forum && !empty subforums && forum.canRead(user)}">
    <div class="forum">
        <h1 class="forum-header"><a href="${forum.link}">${forum.title}</a></h1>
        <div class="table forum-content">
            <c:forEach var="subforum" varStatus="subforumStatus" items="${subforums}">
                <div class="table-row forum-row ${subforumStatus.index % 2 == 0 ? 'even' : 'odd'}">
                    <div class="table-cell topic">
                        <h2><a href="${subforum.link}">${subforum.title}</a></h2>
                        <c:forEach var="subsubforum" varStatus="subsubforumStatus" items="${subforum.getChildren(user)}">
                            <c:if test="${subsubforumStatus.index > 0}">, </c:if>
                            <c:if test="${subsubforumStatus.index == 0}">└ </c:if>
                            <a href="${subsubforum.link}" class="subsubforum">${subsubforum.title}</a></c:forEach>
                        <h3>${subforum.description}</h3>
                    </div>
                    <div class="table-cell count">
                        ${subforum.formattedTopicCount}<br />
                        ${subforum.formattedPostCount}
                    </div>
                    <div class="table-cell avatar">
                        <c:if test="${!empty subforum.lastPost}">
                            <img class="circle" src="${subforum.lastPost.user.mediumAvatar}" />
                        </c:if>
                    </div>
                    <div class="table-cell lastpost">
                        <c:choose>
                            <c:when test="${empty subforum.lastPost}">
                                <div style="text-align: center">
                                    -   
                                </div>
                            </c:when>
                            <c:otherwise>  
                                <a class="truncate" href="${subforum.lastPost.topic.link}">${subforum.lastPost.topic.title}</a>
                                Przez ${subforum.lastPost.user.formattedNick}<br />
                                ${subforum.lastPost.formattedCreationDate}
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div> 
            </c:forEach>
        </div>
    </div>
</c:if>