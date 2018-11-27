package ru.job4j.presentation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.logic.Action;
import ru.job4j.logic.ValidateService;
import ru.job4j.model.Role;
import ru.job4j.model.User;
import ru.job4j.persistent.DbStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet class for update users.
 */
public class UserUpdateServlet extends HttpServlet {
    private final ValidateService logic = ValidateService.getInstance();
    private final Logger logger = LogManager.getLogger(UserCreateServlet.class);


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
        req.setAttribute("login", user.getLogin());
        req.setAttribute("password", user.getPassword());
        req.setAttribute("email", user.getEmail());
        req.setAttribute("roleName", user.getRoleName());
        HttpSession session = req.getSession();
        String role = (String) session.getAttribute("role");
        req.setAttribute("role", role);
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
        if (!req.getParameter("login").trim().equals("") && !req.getParameter("password").trim().equals("")) {
            User user = new User(
                    req.getParameter("login"),
                    req.getParameter("password"),
                    req.getParameter("email")
            );
            Role role = DbStore.getInstance().findRoleByName(req.getParameter("roleName"));
            user.setRoleName(role.getName());
            user.setRoleId(role.getRoleId());
            Role changedRole = DbStore.getInstance().findRoleByName(req.getParameter("role"));
            if (changedRole != null) {
                logger.debug(changedRole.getRoleId() + " " + changedRole.getName());
                user.setRoleName(changedRole.getName());
                user.setRoleId(changedRole.getRoleId());
            }
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
