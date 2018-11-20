package ru.job4j.presentation;

import ru.job4j.logic.Action;
import ru.job4j.logic.ValidateService;
import ru.job4j.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet class for deleting users.
 */
public class UserDeleteServlet extends HttpServlet {
    private final ValidateService service = ValidateService.getInstance();

    /**
     * doGet method.
     * Get id parameter from index.jsp form.
     * Create user model with this id.
     * Call action("DELETE", user) method from ValidateService.class.
     * After deleting redirect to UserServlet.
     * @param req client request.
     * @param resp server response.
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        String action = req.getParameter("action");
        Action.Type actionType = Action.Type.valueOf(action.toUpperCase());
        User deletedUser = new User("", "", "");
        deletedUser.setId(Integer.parseInt(req.getParameter("id")));
        service.init().action(actionType, deletedUser);
        resp.sendRedirect("/");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
