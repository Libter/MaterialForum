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
            <h1>${topic.title}</h1>
            <c:forEach var="post" items="${posts}">
                ${post.text}<hr />
            </c:forEach>
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
        </div>
    </body>
</html>

