$(document).ready(function () {
    CKEDITOR.disableAutoInline = true;

    $("#topic-header").on('keydown', function (e) {
        if (e.keyCode == 13)
        {
            e.preventDefault();
            $("#topic-header").blur();
            editTitle();
        }
    });

    $("#topic-header").on('focusout', function () {
        editTitle();
    });

    $("#newPostForm").validate({
        ignore: [],
        rules: {
            text: {
                required: function () {
                    CKEDITOR.instances.editor.updateElement();
                },
                minlength: 11
            }
        },
        messages: {
            text: {
                required: "Treść tematu nie może być pusta!",
                minlength: "Wpisana treść jest za krótka!"
            }
        }
    });
});

function editTitle() {
    var form = document.createElement('form');
    form.method= 'post';
    form.action = '/topic/' + topicId + './editTitle/';
    
    var input = document.createElement('input');
    input.type = 'hidden';
    input.name = 'title';
    input.value = $("#topic-header").html();
    
    form.appendChild(input);
    document.body.appendChild(form);
    form.submit();
}

function editPost(id) {
    var post = $('#post-' + id);
    var postText = post.find('.text');
    var postEditId = 'post-edit-' + id;

    postText.html('<textarea id="' + postEditId + '">' + postText.html() + '</textarea>');
    post.find('.buttons .edit').hide();
    post.find('.buttons .save').show();
    CKEDITOR.replace(postEditId);
}

function savePost(id) {
    var post = $('#post-' + id);
    var postText = post.find('.text');
    var postEditId = 'post-edit-' + id;
    
    var text = CKEDITOR.instances[postEditId].getData();

    $.ajax({
        url: '/topic/' + topicId + './editPost/',
        method: 'post',
        data: {
            text: text,
            id: id
        },
        success: function() {
            postText.html(text);
            post.find('.buttons .edit').show();
            post.find('.buttons .save').hide();
        }
    });
    
}