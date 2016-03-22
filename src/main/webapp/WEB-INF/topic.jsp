<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/WEB-INF/include/head.jsp" %>
        <script src="/js/newpost.js"></script>
        <script src="/ckeditor/ckeditor.js"></script>
    </head>
    <body>
        <%@ include file="/WEB-INF/include/menu.jsp" %>
        <br />
        <div id="main">
            <h1 class="panel-header">${topic.title}</h1>

            <div id="posts">
                <c:forEach var="post" items="${posts}">
                    <div class="post">
                        <div class="user-info">
                            <b>${post.user.nick}</b><br />
                            <img src="${post.user.largeAvatar}" class="avatar" />
                        </div>
                        <div class="text">
                            ${post.text}
                        </div>
                    </div>
                </c:forEach>
            </div>

            <c:if test="${!empty user}">
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

