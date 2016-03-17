package net.materialforum.servlet;

import java.io.IOException;
import java.util.LinkedHashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.materialforum.yaml.ForumSection;
import net.materialforum.yaml.SectionManager;

@WebServlet("/forum/*")
public class ForumServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String section = request.getRequestURI().split("/")[2];
        LinkedHashMap<String,ForumSection> sections = new SectionManager().getSections();
        request.setAttribute("forums", sections.values());
        request.setAttribute("forum", sections.get(section));
        request.getRequestDispatcher("/WEB-INF/forum.jsp").forward(request, response);
    }

}
