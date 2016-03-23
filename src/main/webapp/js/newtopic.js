$(document).ready(function () {
    $("#newTopicForm").validate({
        ignore: [],
        rules: {
            title: {
                required: true,
                minlength: 3,
                maxlength: 250
            },
            text: {
                required: function() {
                    CKEDITOR.instances.editor.updateElement();
                },
                minlength: 11
            }
        },
        messages: {
            title: {
                required: "Podaj tytuł!",
                minlength: "Podany tytuł jest za krótki!",
                maxlength: "Podany tytuł jest za długi!"
            },
            text: {
                required: "Treść tematu nie może być pusta!",
                minlength: "Wpisana treść jest za krótka!"
            }
        }
    });
});