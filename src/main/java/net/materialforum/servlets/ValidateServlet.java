package net.materialforum.servlets;

import net.materialforum.entities.UserManager;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/validate/*")
public class ValidateServlet extends BaseServlet {

    @Override
    protected void post(HttpServletRequest request, HttpServletResponse r) throws Exception {
        try {
            String[] splitted = request.getRequestURI().split("/");
            String value = request.getParameter("value");
            String object = splitted[2], field = splitted[3];
            
            if (object.equals("register"))
                response(r, !UserManager.fieldExists(field, value));
            else if (object.equals("login"))
                if (field.equals("nickOrEmail"))
                    response(r, UserManager.fieldExists("nick", value) || UserManager.fieldExists("email", value));
                else if (field.equals("password"))
                    response(r, UserManager.login(request.getParameter("nickOrEmail"), value) != null);
            
        } catch(ArrayIndexOutOfBoundsException | NullPointerException ex) { }
    }
    
    private void response(HttpServletResponse r, boolean b) throws IOException {
        r.getWriter().write(String.valueOf(b));
    }

}
