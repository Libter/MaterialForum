$(document).ready(function () {
    CKEDITOR.disableAutoInline = true;

    $('select').material_select();

    var header = $('#topic-header');

    header.on('keydown', function (e) {
        if (e.keyCode == 13) {
            e.preventDefault();
            $('#topic-header').blur();
            editTitle();
        }
    });

    header.on('paste', function (e) {
        e.preventDefault();
        var text = (e.originalEvent || e).clipboardData.getData('text/plain');
        document.execCommand('insertText', false, text);
    });

    header.on('focusout', function () {
        editTitle();
    });

    $('#newPostForm').validate({
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
                required: 'Treść tematu nie może być pusta!',
                minlength: 'Wpisana treść jest za krótka!'
            }
        }
    });
});

function submit(action, value) {
    var form = document.createElement('form');
    form.method = 'post';
    form.action = '/topic/' + topicId + './' + action + '/';

    var input = document.createElement('input');
    input.type = 'hidden';
    input.name = 'value';
    input.value = value;

    form.appendChild(input);
    document.body.appendChild(form);
    form.submit();
}

function editTitle() {
    submit('editTitle', $('#topic-header').text());
}

function hideTopic() {
    submit('hideTopic', '');
}

function deleteTopic() {
    submit('deleteTopic', '');
}

function likePost(id) {
    var post = $('#post-' + id);
    
    $.ajax({
        url: '/topic/' + topicId + './likePost/',
        method: 'post',
        data: {
            id: id
        },
        success: function () {
            post.find('.buttons .like').hide();
            post.find('.buttons .unlike').show();
        }
    });
}

function unlikePost(id) {
    var post = $('#post-' + id);
    
    $.ajax({
        url: '/topic/' + topicId + './unlikePost/',
        method: 'post',
        data: {
            id: id
        },
        success: function () {
            post.find('.buttons .like').show();
            post.find('.buttons .unlike').hide();
        }
    });
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
        success: function () {
            postText.html(text);
            post.find('.buttons .edit').show();
            post.find('.buttons .save').hide();
        }
    });

}

function deletePost(id) {
    var post = $('#post-' + id);
    
    $.ajax({
        url: '/topic/' + topicId + './deletePost/',
        method: 'post',
        data: {
            id: id
        },
        success: function () {
            post.remove();
        }
    });
}