package net.materialforum.servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.materialforum.beans.NavigationBean;
import net.materialforum.entities.ForumEntity;

@WebServlet("")
public class IndexServlet extends BaseServlet {
    
    @Override
    protected void get(HttpServletRequest request, HttpServletResponse response) throws Exception {    
        request.setAttribute("forums", ForumEntity.getAllForums());
        request.setAttribute("navigation", NavigationBean.index());
        request.getRequestDispatcher("WEB-INF/index.jsp").forward(request, response);
    }

}
