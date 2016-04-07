<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<c:if test="${!empty post.likes}">
    Polubienia: 
    <c:forEach var="like" varStatus="likeStatus" items="${post.likes}">
        <c:if test="${likeStatus.index > 0}">, </c:if>
            ${like.user.formattedNick}
    </c:forEach>
</c:if>