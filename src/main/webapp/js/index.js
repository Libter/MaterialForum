$(document).ready(function () {
    $('.modal-trigger').leanModal();

    $.validator.setDefaults({
        errorClass: 'invalid',
        validClass: 'valid',
        errorPlacement: function (error, element) {
            $(element).parent().find('label').attr('data-error', error.text());
        }
    });

    $("#registerForm").validate({
        rules: {
            nick: {
                required: true,
                minlength: 3,
                remote: {
                    url: '/validate/register/nick/',
                    type: 'post',
                    data: {
                        value: function () {
                            return $('#registerForm :input[name="nick"]').val();
                        }
                    }
                }
            },
            password: {
                required: true,
                minlength: 4
            },
            email: {
                required: true,
                minlength: 5,
                remote: {
                    url: '/validate/register/email/',
                    type: 'post',
                    data: {
                        value: function () {
                            return $('#registerForm :input[name="email"]').val();
                        }
                    }
                }
            }
        },
        messages: {
            nick: {
                required: "Podaj swój nick!",
                minlength: "Twój nick jest za krótki!",
                remote: "Ten nick jest zajęty!"
            },
            password: {
                required: "Podaj swoje hasło!",
                minlength: "Twoje hasło jest za krótkie!"
            },
            email: {
                required: "Podaj swój adres e-mail!",
                email: "Podaj poprawny adres e-mail",
                remote: "Ten adres e-mail jest zajęty!"
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