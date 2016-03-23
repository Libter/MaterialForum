<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>

<c:set var="subforums" value="${forum.getSubforums(forums, user)}" />
<c:if test="${!empty forum && !empty subforums && forum.canRead(user)}">
    <div class="forum">
        <h1 class="panel-header"><a href="${forum.link}">${forum.title}</a></h1>
        <div class="panel-content">
            <c:forEach var="subforum" varStatus="subforumStatus" items="${subforums}">
                <div class="panel-entry ${subforumStatus.index % 2 == 0 ? 'even' : 'odd'}">
                    <div>
                        <h2><a href="${subforum.link}">${subforum.title}</a></h2>
                        <c:forEach var="subsubforum" varStatus="subsubforumStatus" items="${subforum.getSubforums(forums, user)}">
                            <c:if test="${subsubforumStatus.index > 0}">, </c:if>
                            <c:if test="${subsubforumStatus.index == 0}">└ </c:if>
                            <a href="${subsubforum.link}" class="subsubforum">${subsubforum.title}</a></c:forEach>
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
            </c:forEach>
        </div>
    </div>
</c:if>