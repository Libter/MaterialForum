package net.materialforum.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.materialforum.beans.NavigationBean;
import net.materialforum.entities.ForumManager;

@WebServlet("")
public class IndexServlet extends BaseServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
        
        request.setAttribute("forums", ForumManager.getForums());
        request.setAttribute("navigation", NavigationBean.index());
        request.getRequestDispatcher("WEB-INF/index.jsp").forward(request, response);
    }

}
