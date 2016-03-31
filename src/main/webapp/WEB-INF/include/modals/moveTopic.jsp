<%@page contentType="text/html" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="moveTopic" class="modal">
    <form id="moveTopicForm" method="post" action="${topic.moveLink}">
        <div class="modal-content">
            <h4>Przenoszenie tematu</h4>
            <div class="input-field col s12">
                <select name="forum">
                    <c:forEach var="forum" items="${forums}">
                        <c:if test="${empty forum.parent && forum.canRead(user)}">
                            <optgroup label="${forum.title}">
                                <%@ include file="/WEB-INF/include/modals/moveTopic_options.jsp" %>
                            </optgroup>
                        </c:if>
                    </c:forEach>
                </select>
            </div>
            <br /><br /><br /><br /><br /><br /><br /><br /><br /><br />
        </div>
        <div class="modal-footer">
            <button type="submit" class="modal-action waves-effect btn-flat">Przenieś</button>
            <button class="modal-action modal-close waves-effect btn-flat">Anuluj</button>
        </div>
    </form>
</div>