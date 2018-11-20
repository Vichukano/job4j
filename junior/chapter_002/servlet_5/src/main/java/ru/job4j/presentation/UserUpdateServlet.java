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
 * Servlet class for update users.
 */
public class UserUpdateServlet extends HttpServlet {
    private final ValidateService logic = ValidateService.getInstance();

    /**
     * doGet method.
     * Get id of user model from request.
     * Find user in DB with received id.
     * Set attributes of user to form in UserUpdateServlet.
     *
     * @param req  client request.
     * @param resp server response.
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        int id = Integer.parseInt(req.getParameter("id"));
        User user = logic.findById(id);
        req.setAttribute("id", user.getId());
        req.setAttribute("name", user.getName());
        req.setAttribute("login", user.getLogin());
        req.setAttribute("email", user.getEmail());
        req.getRequestDispatcher("/WEB-INF/views/update.jsp").forward(req, resp);
    }

    /**
     * doPost method.
     * Get user parameters for update from form of update.jsp.
     * Update user in DB.
     * Redirect to UserServlet, if user updated, else do nothing.
     *
     * @param req  client request.
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
            String idFromRequest = req.getParameter("id");
            int id;
            if (!idFromRequest.equals("")) {
                id = Integer.parseInt(idFromRequest);
                user.setId(id);
            }
            logic.init().action(actionType, user);
            resp.sendRedirect("/");
        } else {
            resp.sendRedirect("/update?id=" + req.getParameter("id"));
        }
    }
}
