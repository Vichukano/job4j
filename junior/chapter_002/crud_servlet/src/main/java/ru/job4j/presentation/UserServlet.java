package ru.job4j.presentation;

import ru.job4j.logic.Validate;
import ru.job4j.logic.ValidateService;
import ru.job4j.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UserServlet extends HttpServlet {
    private static final String ADD_ACTION = "add";
    private static final String DELETE_ACTION = "delete";
    private static final String UPDATE_ACTION = "update";
    private final Validate logic = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        for (User u : logic.getUsers()) {
            writer.append(u.toString());
            writer.append("<br>");
            writer.flush();
        }
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("action").equals(ADD_ACTION)) {
            this.logic.add(new User(
                    req.getParameter("name"),
                    req.getParameter("login"),
                    req.getParameter("email")
            ));
        } else if (req.getParameter("action").equals(DELETE_ACTION)) {
            int id = Integer.parseInt(req.getParameter("id"));
            this.logic.delete(id);
        } else if (req.getParameter("action").equals(UPDATE_ACTION)) {
            int id = Integer.parseInt(req.getParameter("id"));
            User user = new User(
                    req.getParameter("name"),
                    req.getParameter("login"),
                    req.getParameter("email")
            );
            this.logic.update(id, user);
        }
        resp.sendRedirect("index.html");
    }
}
