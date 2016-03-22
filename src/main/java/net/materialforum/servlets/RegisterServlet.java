package net.materialforum.servlets;

import net.materialforum.entities.UserManager;
import net.materialforum.utils.Validator;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.materialforum.utils.StringUtils;

@WebServlet("/register/")
public class RegisterServlet extends BaseServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
        
        try {
            String nick = StringUtils.removeHtml(request.getParameter("nick"));
            String email = StringUtils.removeHtml(request.getParameter("email"));
            String password = request.getParameter("password");
            
            Validator.lengthOrEmpty(nick, 3, 255);
            Validator.lengthOrEmpty(password, 4, 255);
            Validator.lengthOrEmpty(email, 0, 255);
            Validator.email(email);
            
            Validator.User.nickExists(nick);
            Validator.User.emailExists(email);
            
            UserManager.register(nick, email, password);
            
            request.getSession().setAttribute("user", UserManager.login(nick, password));
            
            response.sendRedirect(request.getHeader("referer"));
        } catch (Validator.ValidationException ex) {
            Validator.message(response);
        }
    }
}
