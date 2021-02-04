import ru.chatserver.persists.User;
import ru.chatserver.persists.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@WebServlet("/user/*")
public class UserServlet extends HttpServlet {

    private UserRepository userRepository;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = null;
        resp.getWriter().println(writeTag("Информация о пользователе", "h1"));
        if(Objects.nonNull(req.getPathInfo())) {
            user = userRepository.findBydId(Integer.valueOf(req.getPathInfo().substring(1)));
            resp.getWriter().println(writeTag("ID: " + user.getId(),"p"));
            resp.getWriter().println(writeTag("Name: " + user.getUsername(), "p"));
            resp.getWriter().println(writeTag("Info: " + user.getInfo(), "p"));
        } else {
            resp.getWriter().println(writeTag("format request url  /user/{id} example /user/1", "p"));
        }
    }

    @Override
    public void init() throws ServletException {
        this.userRepository = (UserRepository) getServletContext().getAttribute("userRepository");
    }

    private String writeTag(String msg, String tag) {
        return new StringBuilder().append("<").append(tag).append(">").append(msg).append("</").append(tag).append(">").toString();
    }
}
