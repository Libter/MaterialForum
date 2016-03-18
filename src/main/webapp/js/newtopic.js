$(document).ready(function () {
    $("#newTopicForm").validate({
        ignore: [],
        rules: {
            title: {
                required: true,
                minlength: 3
            },
            text: {
                required: true,
                minlength: 3
            }
        },
        messages: {
            title: {
                required: "Podaj tytuł!",
                minlength: "Podany tytuł jest za krótki!",
            },
            text: {
                required: "Treść tematu nie może być pusta!",
                minlength: "Wpisana treść jest za krótka!",
            }
        }
    });
    
    $("#loginForm").validate({
        rules: {
            nickOrEmail: {
                required: true,
                remote: {
                    url: '/validate/login/nickOrEmail/',
                    type: 'post',
                    data: {
                        value: function () {
                            return $('#loginForm :input[name="nickOrEmail"]').val();
                        }
                    }
                }
            },
            password: {
                required: true,
                remote: {
                    url: '/validate/login/password/',
                    type: 'post',
                    data: {
                        value: function () {
                            return $('#loginForm :input[name="password"]').val();
                        },
                        nickOrEmail: function () {
                            return $('#loginForm :input[name="nickOrEmail"]').val();
                        }
                    }
                }
            }
        },
        messages: {
            nickOrEmail: {
                required: "Podaj swój nick lub adres e-mail!",
                remote: "Nie istnieje użytkownik o takim nicku lub adresie e-mail!"
            },
            password: {
                required: "Podaj swoje hasło!",
                remote: "Nieprawidłowe hasło!"
            }
        }
    });
});