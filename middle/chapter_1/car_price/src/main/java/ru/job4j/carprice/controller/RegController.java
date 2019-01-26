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
 * Servlet for registration new user.
 */
public class RegController extends HttpServlet {
    private final Logger logger = LogManager.getLogger(RegController.class);
    private final UserService userService = UserService.getInstance();

    /**
     * Method for redirect to registration.html page.
     *
     * @param req  client request.
     * @param resp server response - registration.html page.
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("registration.html");
    }

    /**
     * Method for adding new user to database.
     * Before adding check password and existing user.
     * You can't registry user with login that already exist.
     * When add new user, then store id and login in session.
     *
     * @param req  client request with user parameters.
     * @param resp server response.
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String login = req.getParameter("login");
        String pass = req.getParameter("password");
        String confirm = req.getParameter("confirm");
        if (pass.equals(confirm)) {
            User user = new User(login, pass);
            logger.debug("New user: {}", user.toString());
            if (!this.userService.isExist(user)) {
                this.userService.add(user);
                session.setAttribute("id", user.getId());
                session.setAttribute("login", login);
                resp.sendRedirect("index.html");
            } else {
                this.doGet(req, resp);
            }
        } else {
            logger.error("Password does not match!");
        }
    }
}
