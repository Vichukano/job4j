package ru.job4j.carprice.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.carprice.model.User;
import ru.job4j.carprice.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet for login.html page.
 */
public class LoginController extends HttpServlet {
    private final Logger logger = LogManager.getLogger(LoginController.class);
    private final UserService userService = UserService.getInstance();

    /**
     * Method for redirecting to login.html page.
     * When redirect, then close current session.
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        session.invalidate();
        req.getRequestDispatcher("login.html").forward(req, resp);
    }

    /**
     * Method for login user.
     * Create new session.
     * Before redirect check user login and password.
     * If checked true, then redirect to index.html page
     * and save user id and login parameters in session.
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if (this.userService.isCredential(login, password)) {
            User user = this.userService.findByLogin(login);
            session.setAttribute("id", user.getId());
            session.setAttribute("login", user.getLogin());
            logger.debug("Found user: {}", user.toString());
            resp.sendRedirect("index.html");
        } else {
            resp.sendRedirect("/login");
        }
    }
}
