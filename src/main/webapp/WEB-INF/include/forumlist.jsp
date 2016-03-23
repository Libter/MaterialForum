<%@page contentType="text/html" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<c:if test="${!empty forum && forum.canRead(user)}">
    <div class="forum">
        <h1 class="panel-header"><a href="${forum.link}">${forum.title}</a></h1>
        <div class="panel-content">
            <c:set var="subforumCount" value="0" scope="page" />
            <c:forEach var="subforum" items="${forums}">
                <c:if test="${forum.id == subforum.parent.id && subforum.canRead(user)}">
                    <div class="panel-entry ${subforumCount % 2 == 0 ? 'even' : 'odd'}">
                        <div>
                            <h2><a href="${subforum.link}">${subforum.title}</a></h2>
                            <c:set var="subsubforumStarted" value="false" scope="page" />
                            <c:forEach var="subsubforum" items="${forums}">
                                <c:if test="${subforum.id == subsubforum.parent.id && subsubforum.canRead(user)}">
                                    <c:if test="${subsubforumStarted}">, </c:if>
                                <c:if test="${!subsubforumStarted}">
                                    └
                                    <c:set var="subsubforumStarted" value="true" scope="page" />
                                </c:if>
                                <a href="${subsubforum.link}" class="subsubforum">${subsubforum.title}</a></c:if>
                            </c:forEach>
                                <h3>${subforum.description}</h3>
                        </div>
                        <div class="count">
                            ${subforum.topicCount} tematów<br />
                            ${subforum.postCount} postów
                        </div>
                        <div class="avatar">
                            <c:if test="${!empty subforum.lastPost}">
                                <img src="${subforum.lastPost.user.smallAvatar}" />
                            </c:if>
                        </div>
                        <div class="lastpost">
                            <c:choose>
                                <c:when test="${empty subforum.lastPost}">
                                    <div style="text-align: center">
                                        -   
                                    </div>
                                </c:when>
                                <c:otherwise>  
                                    <a href="${subforum.lastPost.topic.link}">${subforum.lastPost.topic.title}</a><br />
                                    Przez ${subforum.lastPost.user.formattedNick}<br />
                                    ${subforum.lastPost.formattedCreationDate}
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                    <c:set var="subforumCount" value="${subforumCount + 1}" scope="page" />            
                </c:if>
            </c:forEach>
        </div>
    </div>
</c:if>