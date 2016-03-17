package net.materialforum.servlet;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.materialforum.yaml.ForumSection;
import net.materialforum.yaml.SectionManager;

@WebServlet("")
public class IndexServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<ForumSection> sections = new SectionManager().getSections();
        
        request.setAttribute("forums", sections);
        request.setAttribute("user", request.getSession().getAttribute("user"));
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

}
