<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/include/head.jsp" %>
        <script src="/js/topic.js"></script>
        <script src="/ckeditor/ckeditor.js"></script>
        <script>var topicId = ${topic.id}</script>
    </head>
    <body>
        <%@ include file="/WEB-INF/include/menu.jsp" %>
        <br />
        <div id="main">
            <h1 id="topic-header" class="panel-header" contenteditable="true">${topic.title}</h1>

            <div id="posts">
                <c:forEach var="post" items="${posts}">
                    <div class="post" id="post-${post.id}">
                        <div class="body">
                            <div class="user-info">
                                <b>${post.user.formattedNick}</b><br />
                                <img src="${post.user.largeAvatar}" class="avatar" />
                            </div>
                            <div class="text">
                                ${post.text}
                            </div>
                        </div>
                        <div class="buttons">
                            <button class="edit waves-effect btn-flat" onclick="editPost(${post.id});">Edytuj</button>
                            <button class="save waves-effect btn-flat" onclick="savePost(${post.id});">Zapisz</button>
                        </div>
                    </div>
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
        </div>
    </body>
</html>

