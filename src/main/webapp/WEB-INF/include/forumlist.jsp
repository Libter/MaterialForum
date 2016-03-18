<%@page contentType="text/html" pageEncoding="utf-8"%>
<ul class="collection with-header forum">
    <li class="collection-header"><h5><a href="${forum.link}">${forum.title}</a></h5></li>
    <c:forEach var="subforum" items="${forums}">
        <c:if test="${forum.name.equals(subforum.parent.name)}">
            <li class="collection-item subforum">
                <a href="${subforum.link}">${subforum.title}</a><br />
                <c:forEach var="subsubforum" items="${forums}">
                    <c:if test="${subforum.name.equals(subsubforum.parent.name)}">
                        <a href="${subsubforum.link}" class="subsubforum">
                            <i class="fa fa-circle-o"></i> ${subsubforum.title}
                        </a>&nbsp;
                    </c:if>
                </c:forEach>
            </li>
        </c:if>
    </c:forEach>
</ul>