package ru.job4j.presentation;

import ru.job4j.logic.Action;
import ru.job4j.logic.ValidateService;
import ru.job4j.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UserUpdateServlet extends HttpServlet {
    private final ValidateService logic = ValidateService.getInstance();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        int id = Integer.parseInt(req.getParameter("id"));
        User user = logic.findById(id);
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.append("<!DOCTYPE html>"
                + "<html lang='en' xmlns='http://www.w3.org/1999/html'>"
                + "<head>"
                + "    <meta charset='UTF-8'>"
                + "    <title>Update User</title>"
                + "</head>"
                + "<body>"
                + "    <form method='post'>"
                + "        <input type='hidden' name='action' value='update'>"
                + "        <input type='hidden' name='id' value='" + user.getId() + "'>"
                + "        Name:"
                + "        <input type='text' name='name' value='" + user.getName() + "'><br>"
                + "        Login:"
                + "        <input type='text' name='login' value='" + user.getLogin() + "'><br>"
                + "        Email:"
                + "        <input type='text' name='email' value='" + user.getEmail() + "'><br>"
                + "        <br>"
                + "        <br>"
                + "        <input type='submit' value='UPDATE'>"
                + "    </form>"
                + "    <br>"
                + "    <br>"
                + "</body>"
                + "</html>");
        writer.flush();
        writer.close();
    }


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
