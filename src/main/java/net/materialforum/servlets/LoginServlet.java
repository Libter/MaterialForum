package net.materialforum.servlets;

import net.materialforum.entities.UserManager;
import net.materialforum.utils.Validator;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.materialforum.utils.StringUtils;

@WebServlet("/login/")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        try {
            String nickOrEmail = StringUtils.removeHtml(request.getParameter("nickOrEmail"));
            String password = request.getParameter("password");
            
            Validator.lengthOrEmpty(nickOrEmail, 0, 255);
            Validator.lengthOrEmpty(password, 0, 255);
            
            request.getSession().setAttribute("user", UserManager.login(nickOrEmail, password));
            
            response.sendRedirect(request.getHeader("referer"));
        } catch (Validator.ValidationException ex) {
            Validator.message(response);
        }
    }
}
