<%@page contentType="text/html" pageEncoding="utf-8"%>
<ul class="collection with-header">
    <li class="collection-header"><h5><a href="/forum/${forum.urlName}">${forum.displayName}</a></h5></li>
    <c:forEach var="subforum" items="${forums}">
        <c:if test="${forum.name.equals(subforum.parent.name)}">
            <li class="collection-item">
                <a href="/forum/${subforum.urlName}">${subforum.displayName}</a><br />
                <c:forEach var="subsubforum" items="${forums}">
                    <c:if test="${subforum.name.equals(subsubforum.parent.name)}">
                        <a href="/forum/${subsubforum.urlName}" class="subsubforum">
                            <i class="fa fa-circle-o"></i> ${subsubforum.displayName}
                        </a>&nbsp;
                    </c:if>
                </c:forEach>
            </li>
        </c:if>
    </c:forEach>
</ul>