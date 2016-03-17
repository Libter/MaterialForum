package net.materialforum.servlet;

import net.materialforum.sql.UserManager;
import net.materialforum.util.Validator;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login/")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String nickOrEmail = request.getParameter("nickOrEmail");
            String password = request.getParameter("password");
            
            Validator.lengthOrEmpty(nickOrEmail, 0, 255);
            Validator.lengthOrEmpty(password, 0, 255);
            
            request.getSession().setAttribute("user", UserManager.login(nickOrEmail, password));
            
            response.sendRedirect("/");
        } catch (Validator.ValidationException ex) {
            Validator.message(response);
        }
    }
}
