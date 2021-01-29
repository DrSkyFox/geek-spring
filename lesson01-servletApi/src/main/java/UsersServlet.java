import persists.User;
import persists.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/users")
public class UsersServlet extends HttpServlet {

    private UserRepository userRepository;

    @Override
    public void init() throws ServletException {
        this.userRepository = (UserRepository) getServletContext().getAttribute("userRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("<h1>Users</h1>");
        resp.getWriter().println("<ul>");
        for (User user:
             userRepository.findAll()) {
            resp.getWriter().println(String.format("<li><a href = \"%s\">UserName: %s</a></li>",
                    req.getContextPath() + "/user/" + user.getId(),
                    user.getUsername()));
        }
        resp.getWriter().println("</ul>");
    }
}
