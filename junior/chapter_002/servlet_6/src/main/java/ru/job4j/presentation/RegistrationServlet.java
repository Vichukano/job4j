package ru.job4j.presentation;

import ru.job4j.logic.Action;
import ru.job4j.logic.Validate;
import ru.job4j.logic.ValidateService;
import ru.job4j.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet class for registration.
 * When new user registrate set his role to user.
 */
public class RegistrationServlet extends HttpServlet {
    private final Validate service = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        req.getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String confirm = req.getParameter("confirm");
        String email = req.getParameter("email");
        String action = req.getParameter("action");
        Action.Type actionType = Action.Type.valueOf(action.toUpperCase());
        if (!req.getParameter("login").trim().equals("") && !req.getParameter("password").trim().equals("")) {
            if (password.equals(confirm)) {
                if (!this.service.isExist(login)) {
                    User user = new User(login, password, email);
                    user.setRoleId(2);
                    this.service.init().action(actionType, user);
                    user = this.service.findByLogin(login);
                    session.setAttribute("login", login);
                    session.setAttribute("role", user.getRoleName());
                    session.setAttribute("id", user.getId());
                    req.getRequestDispatcher("/").forward(req, resp);
                } else {
                    req.setAttribute("error", "User with this login already exists!");
                    doGet(req, resp);
                }
            } else {
                req.setAttribute("error", "Password does not match!");
                doGet(req, resp);
            }
        } else {
            req.setAttribute("error", "Fill all fields!");
            doGet(req, resp);
        }
    }
}
