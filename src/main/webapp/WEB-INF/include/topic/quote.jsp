<%@page contentType="text/html" pageEncoding="utf-8"%>
<blockquote <!--contenteditable="false"!-->>
    <div class="header">${post.formattedCreationDate} ${post.user.formattedNick} napisał:</div>
    ${post.text}
</blockquote>
<br />