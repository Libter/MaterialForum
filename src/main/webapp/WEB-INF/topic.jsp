<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="utf-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/include/head.jsp" %>
        <script src="/js/topic.js"></script>
        <script src="/ckeditor/ckeditor.js"></script>
        <script>var topicId = ${topic.id};</script>
    </head>
    <body>
        <%@ include file="/WEB-INF/include/menu.jsp" %>
        <br />
        <div id="main">
            <h1 id="topic-header" class="forum-header" 
                contenteditable="${topic.forum.canEditTopic(user, topic)}">
                ${topic.title}
            </h1>

            <div id="posts">
                <c:forEach var="post" items="${posts}" varStatus="postStatus">
                    <div class="post" id="post-${post.id}">
                        <div class="body">
                            <div class="user-info">
                                <b>${post.user.formattedNick}</b><br />
                                <img class="circle" src="${post.user.largeAvatar}" />
                            </div>
                            <div class="content">
                                <div class="date">Napisano ${post.formattedCreationDate}</div>
                                <div class="text">
                                    ${post.text}
                                </div>
                                <div class="likes">
                                    <%@include file="/WEB-INF/include/topic/likes.jsp" %>
                                </div>
                            </div>
                        </div>
                        <%@ include file="/WEB-INF/include/topic/buttons.jsp" %>
                </c:forEach>
            </div>

            <c:if test="${topic.forum.canWritePosts(user)}">
                <br />
                <form id="newPostForm" method="post" action="${topic.addLink}">
                    <textarea name="text" id="editor"></textarea>
                    <label class="error"></label>
                    <script>CKEDITOR.replace('editor');</script>
                    <br />
                    <button type="submit" class="btn waves-effect waves-light">
                        Odpowiedz <i class="material-icons right">send</i> 
                    </button>
                </form>
            </c:if>

            <c:if test="${topic.forum.canMoveTopic(user, topic)}">
                <%@ include file="/WEB-INF/include/modals/moveTopic.jsp" %>
            </c:if>
        </div>
    </body>
</html>

