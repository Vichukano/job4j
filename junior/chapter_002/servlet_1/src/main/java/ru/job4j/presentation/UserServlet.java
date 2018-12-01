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

/**
 * Класс - сервлет.
 */
public class UserServlet extends HttpServlet {
    private final ValidateService logic = ValidateService.getInstance();

    /**
     * Метод возвращает всех пользователей клиенту.
     * @param req Http request
     * @param resp Http response
     * @throws ServletException
     * @throws IOException
     */
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

    /**
     * Метод получает POST запрос со стороны клиента
     * и создает, удаляет, редактирует пользователя с
     * переданными параметрами.
     * @param req Http request
     * @param resp Http response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        Action.Type actionType = Action.Type.valueOf(action.toUpperCase());
        User user = new User(
                req.getParameter("name"),
                req.getParameter("login"),
                req.getParameter("email")
        );
        String idFromRequest = req.getParameter("id");
        int id;
        if (!idFromRequest.equals("") || idFromRequest != null) {
            id = Integer.parseInt(idFromRequest);
            user.setId(id);
        }
        logic.init().action(actionType, user);
        resp.sendRedirect("index.html");
    }
}
