<%@page contentType="text/html" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:forEach var="subforum" items="${forum.getChildren(user)}">
    <option value="${subforum.id}">
        <c:forEach begin="1" end="${param.level}">—</c:forEach>
        ${subforum.title}
    </option>
    <c:set var="forum" value="${subforum}" scope="request" />
    <jsp:include page="/WEB-INF/include/modals/moveTopic_options.jsp">
        <jsp:param name="level" value="${param.level + 1}" />
    </jsp:include>
</c:forEach>