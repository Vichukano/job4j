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

public class RegController extends HttpServlet {
    private final Logger logger = LogManager.getLogger(RegController.class);
    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("registration.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String login = req.getParameter("login");
        String pass = req.getParameter("password");
        String confirm = req.getParameter("confirm");
        if(pass.equals(confirm)) {
            User user = new User(login, pass);
            logger.debug("New user: {}", user.toString());
            if (!this.userService.isExist(user)) {
                this.userService.add(user);
                session.setAttribute("id", user.getId());
                session.setAttribute("login", login);
                req.getRequestDispatcher("/cars").forward(req, resp);
            } else {
                this.doGet(req, resp);
            }
        } else {
            logger.error("Password does not match!");
        }
    }
}
