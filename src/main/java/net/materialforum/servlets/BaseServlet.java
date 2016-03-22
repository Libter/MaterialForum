package net.materialforum.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.materialforum.entities.UserEntity;
import net.materialforum.entities.UserManager;
import net.materialforum.utils.Validator.ValidationException;

public abstract class BaseServlet extends HttpServlet {
    
    protected UserEntity user;
    
    private void init(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        
        Long userId = (Long) request.getSession().getAttribute("userId");
        if (userId != null)
            user = UserManager.findById(userId);
        else
            user = null;
        request.setAttribute("user", user);
    }
    
    protected void get(HttpServletRequest request, HttpServletResponse response) throws ValidationException { }
    
    protected void post(HttpServletRequest request, HttpServletResponse response) throws ValidationException { }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            init(request, response);
            get(request, response);
        } catch(ValidationException e) { }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         try {
            init(request, response);
            post(request, response);
        } catch(ValidationException e) { }
    }
    
}
