<%@page contentType="text/html" pageEncoding="utf-8"%>
<div class="buttons">
    <c:choose>
        <c:when test="${postStatus.index == 0}">
            <c:if test="${topic.forum.canMoveTopic(user, topic)}">
                <button class="waves-effect btn-flat modal-trigger" href="#moveTopic">
                    <i class="fa fa-arrows"></i>Przenieś
                </button>
            </c:if>
            <c:choose>
                <c:when test="${topic.closed}">
                    <c:if test="${topic.forum.canOpenTopic(user, topic)}">
                        <button class="waves-effect btn-flat" onclick="openTopic();">
                            <i class="fa fa-unlock-alt"></i>Otwórz
                        </button>
                    </c:if>
                </c:when>
                <c:otherwise>
                    <c:if test="${topic.forum.canCloseTopic(user, topic)}">
                        <button class="waves-effect btn-flat" onclick="closeTopic();">
                            <i class="fa fa-lock"></i>Zamknij
                        </button>
                    </c:if>
                </c:otherwise>
            </c:choose>

            <c:if test="${topic.forum.canPinTopic(user, topic)}">
                <button class="waves-effect btn-flat modal-trigger">
                    <i class="fa fa-thumb-tack"></i>Przypnij
                </button>
            </c:if>
            <c:if test="${topic.forum.canDeleteTopic(user, topic)}">
                <button class="waves-effect btn-flat" onclick="deleteTopic();">
                    <i class="fa fa-trash-o"></i>Usuń
                </button>
            </c:if>
        </c:when>
        <c:otherwise>
            <c:if test="${topic.forum.canDeletePost(user, post)}">
                <button class="waves-effect btn-flat" onclick="deletePost(${post.id});">
                    <i class="fa fa-trash-o"></i>Usuń
                </button>
            </c:if>
        </c:otherwise>
    </c:choose>
    <c:if test="${topic.forum.canLikePost(user, post)}">
        <button class="like waves-effect btn-flat <c:if test="${post.hasLiked(user)}">hidden</c:if>" 
    onclick="likePost(${post.id});">
    <i class="fa fa-thumbs-o-up"></i>Polub
</button>
<button class="unlike waves-effect btn-flat <c:if test="${!post.hasLiked(user)}">hidden</c:if>" 
    onclick="unlikePost(${post.id});">
    <i class="fa fa-thumbs-o-down"></i>Odlub
</button>
</c:if>
<c:if test="${topic.forum.canEditPost(user, post)}">
    <button class="edit waves-effect btn-flat" onclick="editPost(${post.id});">
        <i class="fa fa-pencil"></i>Edytuj
    </button>
    <button class="save waves-effect btn-flat" onclick="savePost(${post.id});">
        <i class="fa fa-floppy-o"></i>Zapisz
    </button>
</c:if>
</div>
</div>
