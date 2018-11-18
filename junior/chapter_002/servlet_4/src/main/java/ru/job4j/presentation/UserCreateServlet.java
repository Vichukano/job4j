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
 * Servlet class for adding users.
 */
public class UserCreateServlet extends HttpServlet {
    private final ValidateService logic = ValidateService.getInstance();

    /**
     * Method doGet.
     * Redirect to create.jsp when GET method calls from index.jsp.
     * @param req client request.
     * @param resp server response.
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        resp.sendRedirect("/create.jsp");
    }

    /**
     * doPost method.
     * Get parameters of user name, login and email from create.jsp form.
     * Create user model with this parameters.
     * If name parameter and login parameter is not empty, call method action("ADD", user) from ValidateService.class.
     * After that redirect to index.jsp.
     * @param req client request.
     * @param resp server response.
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        String action = req.getParameter("action");
        Action.Type actionType = Action.Type.valueOf(action.toUpperCase());
        if (!req.getParameter("name").trim().equals("") && !req.getParameter("login").trim().equals("")) {
            User user = new User(
                    req.getParameter("name"),
                    req.getParameter("login"),
                    req.getParameter("email")
            );
            logic.init().action(actionType, user);
            resp.sendRedirect("/index.jsp");
        } else {
            resp.sendRedirect("/create.jsp");
        }
    }
}
