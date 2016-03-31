package net.materialforum.servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.materialforum.beans.Navigation;
import net.materialforum.beans.Widgets;
import net.materialforum.entities.ForumEntity;

@WebServlet("")
public class IndexServlet extends BaseServlet {
    
    @Override
    protected void get(HttpServletRequest request, HttpServletResponse response) throws Exception {    
        request.setAttribute("forums", ForumEntity.getAllForums());
        request.setAttribute("navigation", Navigation.index());
        request.setAttribute("widgets", new Widgets());
        request.setAttribute("title", "Forum");
        request.getRequestDispatcher("WEB-INF/index.jsp").forward(request, response);
    }

}
