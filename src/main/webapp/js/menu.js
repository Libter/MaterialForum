$(document).ready(function () {
    $('.modal-trigger').leanModal();

    $.validator.setDefaults({
        errorClass: 'invalid',
        validClass: 'valid',
        errorPlacement: function (error, element) {
            $(element).parent().find('label').attr('data-error', error.text());
        }
    });

    $('#registerForm').validate({
        rules: {
            nick: {
                required: true,
                minlength: 3,
                maxlength: 250,
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
                minlength: 4,
                maxlength: 250
            },
            email: {
                required: true,
                minlength: 5,
                maxlength: 250,
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
                required: 'Podaj swój nick!',
                minlength: 'Twój nick jest za krótki!',
                maxlength: 'Twój nick jest za długi!',
                remote: 'Ten nick jest zajęty!'
            },
            password: {
                required: 'Podaj swoje hasło!',
                minlength: 'Twoje hasło jest za krótkie!',
                maxlength: 'Twoje hasło jest za długie!'
            },
            email: {
                required: 'Podaj swój adres e-mail!',
                email: 'Podaj poprawny adres e-mail',
                maxlength: 'Twój adres e-mail jest za długi!',
                remote: 'Ten adres e-mail jest zajęty!'
            }
        }
    });
    
    $('#loginForm').validate({
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
                required: 'Podaj swój nick lub adres e-mail!',
                remote: 'Nie istnieje użytkownik o takim nicku lub adresie e-mail!'
            },
            password: {
                required: 'Podaj swoje hasło!',
                remote: 'Nieprawidłowe hasło!'
            }
        }
    });
});