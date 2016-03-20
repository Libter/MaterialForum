package net.materialforum.servlet;

import java.io.IOException;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.materialforum.sql.PostManager;
import net.materialforum.sql.TopicEntity;
import net.materialforum.sql.TopicManager;
import net.materialforum.sql.UserEntity;
import net.materialforum.util.Validator;

@WebServlet("/topic/*")
public class TopicServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {        
        String[] splitted = request.getRequestURI().split("/");
        String topicUrl = splitted[2];
        Long topicId = Long.parseLong(topicUrl.split("\\.")[0]);
        
        TopicEntity topic = TopicManager.findById(topicId);
        if (!URLEncoder.encode(topic.getUrl(), "utf-8").equals(topicUrl))
            response.sendRedirect(topic.getLink());
        else {
            request.setAttribute("topic", topic);
            request.setAttribute("posts", PostManager.getPosts(topic));
            request.getRequestDispatcher("/WEB-INF/topic.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {        
        request.setCharacterEncoding("utf-8");
        
        String[] splitted = request.getRequestURI().split("/");
        String topicUrl = splitted[2];
        Long topicId = Long.parseLong(topicUrl.split("\\.")[0]);
        
        TopicEntity topic = TopicManager.findById(topicId);
        if (!URLEncoder.encode(topic.getUrl(), "utf-8").equals(topicUrl))
            response.sendRedirect(topic.getLink());
        else {
            try {
                String text = request.getParameter("text");
                
                Validator.lengthOrEmpty(text, 11, Integer.MAX_VALUE);
                
                UserEntity user = Validator.User.get(request);
                
                PostManager.create(topic, user, text);
                
                response.sendRedirect(topic.getLink());
            } catch (Validator.ValidationException ex) {
                Validator.message(response);
            }
        }
    }

}
