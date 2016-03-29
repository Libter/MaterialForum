package net.materialforum.servlets;

import java.net.URLEncoder;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.materialforum.beans.NavigationBean;
import net.materialforum.entities.PostEntity;
import net.materialforum.entities.TopicEntity;
import net.materialforum.utils.Database;
import net.materialforum.utils.StringUtils;
import net.materialforum.utils.Validator;

@WebServlet("/topic/*")
public class TopicServlet extends BaseServlet {

    @Override
    protected void get(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] splitted = request.getRequestURI().split("/");
        if (splitted.length < 3) {
            response.sendRedirect("/");
            return;
        }

        String topicUrl = splitted[2];
        Long topicId = Long.parseLong(topicUrl.split("\\.")[0]);

        TopicEntity topic = Database.getById(TopicEntity.class, topicId);
        Validator.Topic.exists(topic);
        Validator.Forum.canRead(topic.getForum(), user);
        if (!URLEncoder.encode(topic.getUrl(), "utf-8").equals(topicUrl)) {
            response.sendRedirect(topic.getLink());
        } else {
            request.setAttribute("topic", topic);
            request.setAttribute("posts", topic.getPosts());
            request.setAttribute("navigation", NavigationBean.topic(topic));
            request.getRequestDispatcher("/WEB-INF/topic.jsp").forward(request, response);
        }
    }

    @Override
    protected void post(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] splitted = request.getRequestURI().split("/");
        if (splitted.length < 4) {
            response.sendRedirect("/");
            return;
        }

        String topicUrl = splitted[2];
        String action = splitted[3];
        Long topicId = Long.parseLong(topicUrl.split("\\.")[0]);

        TopicEntity topic = Database.getById(TopicEntity.class, topicId);
        Validator.Topic.exists(topic);
        Validator.Forum.canRead(topic.getForum(), user);
        
        switch (action) {
            case "editTitle":
                String title = StringUtils.removeHtml(request.getParameter("title"));
                
                Validator.Forum.canEditTopic(topic.getForum(), user, topic);
                Validator.lengthOrEmpty(title, 3, 255);
                
                topic.setTitle(title);
                Database.merge(topic);

                response.sendRedirect(topic.getLink());
                break;
            case "editPost":
                String newText = request.getParameter("text");
                Long postId = Long.parseLong(request.getParameter("id"));
                PostEntity post = Database.getById(PostEntity.class, postId);
                
                Validator.lengthOrEmpty(newText, 11, Integer.MAX_VALUE);
                Validator.Forum.canEditPost(topic.getForum(), user, post);
                
                post.setText(newText);
                Database.merge(post);
                break;
            case "add":
                String text = request.getParameter("text");
                
                Validator.Forum.canWritePosts(topic.getForum(), user);
                Validator.lengthOrEmpty(text, 11, Integer.MAX_VALUE);
                
                PostEntity newPost = new PostEntity();
                newPost.setTopic(topic);
                newPost.setUser(user);
                newPost.setText(text);
                newPost.create();

                response.sendRedirect(topic.getLink());
                break;
        }
    }

}
