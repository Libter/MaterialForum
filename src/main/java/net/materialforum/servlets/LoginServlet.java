package net.materialforum.servlets;

import net.materialforum.entities.UserManager;
import net.materialforum.utils.Validator;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.materialforum.utils.StringUtils;

@WebServlet("/login/")
public class LoginServlet extends BaseServlet {

    @Override
    protected void post(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String nickOrEmail = StringUtils.removeHtml(request.getParameter("nickOrEmail"));
        String password = request.getParameter("password");

        Validator.lengthOrEmpty(nickOrEmail, 0, 255);
        Validator.lengthOrEmpty(password, 0, 255);

        request.getSession().setAttribute("userId", UserManager.login(nickOrEmail, password).getId());

        response.sendRedirect(request.getHeader("referer"));
    }
}
