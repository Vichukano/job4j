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

public class UserCreateServlet extends HttpServlet {
    private final ValidateService logic = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.append("<!DOCTYPE html>"
                + "<html lang='en' xmlns='http://www.w3.org/1999/html'>"
                + "<head>"
                + "    <meta charset='UTF-8'>"
                + "    <title>Create User</title>"
                + "</head>"
                + "<body>"
                + "<form method='post'>"
                + "      <input type='hidden' name='action' value='add'>"
                + "       <input type='hidden' name='id' value=''>"
                + "       Name:"
                + "       <input type='text' name='name' value=''><br>"
                + "       Login:"
                + "       <input type='text' name='login' value=''><br>"
                + "       Email:"
                + "       <input type='text' name='email' value=''><br>"
                + "       <br>"
                + "       <br>"
                + "       <input type='submit' value='SAVE'>"
                + "    </form>"
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
            logic.init().action(actionType, user);
            resp.sendRedirect("/");
        } else {
            resp.sendRedirect("/create");
        }
    }
}
