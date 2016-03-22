package net.materialforum.servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/logout/")
public class LogoutServlet extends BaseServlet {

    @Override
    protected void get(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.getSession().setAttribute("userId", null);
        response.sendRedirect(request.getHeader("referer"));
    }
}