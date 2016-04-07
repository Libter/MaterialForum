package net.materialforum.servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.materialforum.beans.Navigation;
import net.materialforum.beans.Widgets;

@WebServlet("")
public class IndexServlet extends BaseServlet {
    
    @Override
    protected void get(HttpServletRequest request, HttpServletResponse response) throws Exception {    
        request.setAttribute("navigation", Navigation.index());
        request.setAttribute("widgets", new Widgets());
        request.setAttribute("title", "Forum v0.1.5");
        request.getRequestDispatcher("WEB-INF/index.jsp").forward(request, response);
    }

}
