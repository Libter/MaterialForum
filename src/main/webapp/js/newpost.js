$(document).ready(function () {
    $("#newPostForm").validate({
        ignore: [],
        rules: {
            text: {
                required: function() {
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