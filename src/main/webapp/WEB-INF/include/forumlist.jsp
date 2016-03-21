<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="utf-8"%>
<%@page trimDirectiveWhitespaces="true" %>
<div class="forum">
    <h1><a href="${forum.link}">${forum.title}</a></h1>
    <div class="subforums">
        <c:set var="subforumCount" value="0" scope="page" />
        <c:forEach var="subforum" items="${forums}">
            <c:if test="${forum.id == subforum.parent.id}">
                <div class="subforum ${subforumCount % 2 == 0 ? 'even' : 'odd'}">
                    <div>
                        <h2><a href="${subforum.link}">${subforum.title}</a></h2>
                            <c:set var="subsubforumStart" value="false" scope="page" />
                            <c:forEach var="subsubforum" items="${forums}">
                                <c:if test="${subforum.id == subsubforum.parent.id}">
                                    <c:if test="${subsubforumStart}">,</c:if>
                                <c:if test="${!subsubforumStart}">
                                    └
                                    <c:set var="subsubforumStart" value="true" scope="page" />
                                </c:if>
                                <a href="${subsubforum.link}" class="subsubforum">${subsubforum.title}</a></c:if>
                        </c:forEach>
                    </div>
                    <div class="count">
                        ${subforum.postCount} postów<br />
                        ${subforum.topicCount} tematów
                    </div>
                </div>
                <c:set var="subforumCount" value="${subforumCount + 1}" scope="page" />            
            </c:if>
        </c:forEach>
    </div>
</div>