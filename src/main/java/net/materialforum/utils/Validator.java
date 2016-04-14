package net.materialforum.utils;

import net.materialforum.entities.UserManager;
import java.io.IOException;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.materialforum.entities.ForumEntity;
import net.materialforum.entities.LikeEntity;
import net.materialforum.entities.PostEntity;
import net.materialforum.entities.TopicEntity;
import net.materialforum.entities.UserEntity;

public class Validator {

    private Validator() { }

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    public static class ForumError extends Exception {
        
        private final String message;
        
        public ForumError(String message) {
            this.message = message;
        }
        
        public void display(HttpServletRequest request, HttpServletResponse response) {
            try {
                request.setAttribute("error", message);
                request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
            } catch (ServletException | IOException ex) { }
        }
        
    }

    public static void empty(String s) throws ForumError {
        if (s == null || s.isEmpty()) {
            throw new ForumError("Wypełnij wszystkie pola!");
        }
    }

    public static void length(String s, int min, int max) throws ForumError {
        if (s.length() < min || s.length() > max) {
            throw new ForumError("Wartość w jednym z pół jest za krótka lub za długa!");
        }
    }

    public static void email(String s) throws ForumError {
        if (!EMAIL_PATTERN.matcher(s).matches()) {
            throw new ForumError("Podaj poprawny adres e-mail!");
        }
    }

    public static void lengthOrEmpty(String s, int min, int max) throws ForumError {
        empty(s);
        length(s, min, max);
    }

    public static class User {

        private User() { }

        public static void nickExists(String nick) throws ForumError {
            if (UserManager.fieldExists("nick", nick)) {
                throw new ForumError("Użytkownik o takim nicku już istnieje!");
            }
        }

        public static void emailExists(String email) throws ForumError {
            if (UserManager.fieldExists("email", email)) {
                throw new ForumError("Użytkownik o takim adresie e-mail już istnieje!");
            }
        }
    }
    
    public static class Topic {
        
        private Topic() { }
        
        public static void exists(TopicEntity topic) throws ForumError {
            if (topic == null)
                throw new ForumError("Temat, który próbujesz wyświetlić nie istnieje!");
        }
    }

    public static class Forum {

        private Forum() { }

        public static void nullParent(ForumEntity forum) throws ForumError {
            if (forum.getParent() == null) {
                throw new ForumError("Nie można tworzyć tematów w kategoriach!");
            }
        }

        public static void exists(ForumEntity forum) throws ForumError {
            if (forum == null)
                throw new ForumError("Forum, które próbujesz wyświetlić nie istnieje!");
        }
        
        public static void canRead(ForumEntity forum, UserEntity user) throws ForumError {
            if (!forum.canRead(user))
                throw new ForumError("Nie masz uprawnień do odczytania tej zawartości!");
        }
        
        public static void canWriteTopics(ForumEntity forum, UserEntity user) throws ForumError {
            if (!forum.canWriteTopics(user))
                throw new ForumError("Nie masz uprawnień do pisania tematów w tym dziale!");
        }
        
        public static void canWritePosts(ForumEntity forum, UserEntity user, TopicEntity topic) throws ForumError {
            if (!forum.canWritePosts(user, topic))
                throw new ForumError("Nie masz uprawnień do pisania postów w tym dziale!");
        }
        
        public static void canEditTopic(ForumEntity forum, UserEntity user, TopicEntity topic) throws ForumError {
            if (!forum.canEditTopic(user, topic))
                throw new ForumError("Nie masz uprawnień do edycji tego tematu!");
        }
        
        public static void canEditPost(ForumEntity forum, UserEntity user, PostEntity post) throws ForumError {
            if (!forum.canEditPost(user, post))
                throw new ForumError("Nie masz uprawnień do edycji tego posta!");
        }
        
        public static void canDeleteTopic(ForumEntity forum, UserEntity user, TopicEntity topic) throws ForumError {
            if (!forum.canDeleteTopic(user, topic))
                throw new ForumError("Nie masz uprawnień do usunięcia tego tematu!");
        }
        
        public static void canDeletePost(ForumEntity forum, UserEntity user, PostEntity post) throws ForumError {
            if (!forum.canDeletePost(user, post))
                throw new ForumError("Nie masz uprawnień do usunięcia tego posta!");
        }
        
        public static void canLikePost(ForumEntity forum, UserEntity user, PostEntity post) throws ForumError {
            if (!forum.canLikePost(user, post))
                throw new ForumError("Nie masz uprawnień do polubienia tego posta!");
        }
        
        public static void canUnlikePost(ForumEntity forum, UserEntity user, LikeEntity like) throws ForumError {
            if (!forum.canUnlikePost(user, like))
                throw new ForumError("Nie masz uprawnień do odlubienia tego posta!");
        }
        
        public static void canCloseTopic(ForumEntity forum, UserEntity user, TopicEntity topic) throws ForumError {
            if (!forum.canCloseTopic(user, topic))
                throw new ForumError("Nie masz uprawnień do zamknięcia tego tematu!");
        }
        
        public static void canOpenTopic(ForumEntity forum, UserEntity user, TopicEntity topic) throws ForumError {
            if (!forum.canCloseTopic(user, topic))
                throw new ForumError("Nie masz uprawnień do otwarcia tego tematu!");
        }

    }

}
