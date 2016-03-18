package net.materialforum.servlet;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.materialforum.sql.TopicManager;
import net.materialforum.sql.UserEntity;
import net.materialforum.util.Validator;
import net.materialforum.yaml.ForumEntity;
import net.materialforum.yaml.ForumManager;

@WebServlet("/forum/*")
public class ForumServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ForumManager manager = new ForumManager();
        String[] splitted = request.getRequestURI().split("/");
        
        if (splitted.length < 3) {
            response.sendRedirect("/");
            return;
        }
        
        String forumUrl = splitted[2];
        ForumEntity forum = manager.findByUrl(forumUrl);
        
        request.setAttribute("forum", forum);
        
        if (splitted.length > 3) {
            String action = splitted[3];
            if (action.equals("add")) {
                request.getRequestDispatcher("/WEB-INF/newthread.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("forums", manager.getForums());
            request.setAttribute("topics", TopicManager.getTopics(forum.getId()));
            request.getRequestDispatcher("/WEB-INF/forum.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ForumManager manager = new ForumManager();
        String[] splitted = request.getRequestURI().split("/");
        String forum = splitted[2];
        
        if (splitted.length > 3) {
            String action = splitted[3];
            if (action.equals("add")) {
                try {
                    String title = request.getParameter("title");
                    String text = request.getParameter("text");
                    
                    Validator.lengthOrEmpty(title, 3, 255);
                    Validator.lengthOrEmpty(text, 3, Integer.MAX_VALUE);
                    
                    UserEntity user = Validator.User.get(request);
                    
                    TopicManager.create(manager.findByUrl(forum).getId(), user, title, text);
                    
                    response.sendRedirect("/forum/" + forum);
                } catch (Validator.ValidationException ex) {
                    Validator.message(response);
                }
            }
        }
    }

}
