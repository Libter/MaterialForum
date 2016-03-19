$(document).ready(function () {
    $("#newPostForm").validate({
        ignore: [],
        rules: {
            text: {
                required: true,
                minlength: 10
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