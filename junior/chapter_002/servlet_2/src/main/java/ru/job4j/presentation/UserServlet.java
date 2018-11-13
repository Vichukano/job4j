package ru.job4j.presentation;

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
     *
     * @param req  Http request
     * @param resp Http response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        StringBuilder sb = new StringBuilder("<table border='1'>");
        sb.append("<tr><td align='center'>Name</td><td align='center'>Login</td><td align='center'>Email</td></tr>");
        for (User u : logic.findAll()) {
            sb.append("<tr>"
                    + "<td>" + u.getName() + "</td>"
                    + "<td>" + u.getLogin() + "</td>"
                    + "<td>" + u.getEmail() + "</td>"
                    + "<td><form method='get' action='/update'>"
                    + "<input type='hidden' name='id' value='" + u.getId() + "'>"
                    + "<input type='submit' value='UPDATE'>"
                    + "</form>"
                    + "</td>"
                    + "</tr>");
        }
        sb.append("</table>");
        writer.append("<!DOCTYPE html>"
                + "<html lang='en' xmlns='http://www.w3.org/1999/html'>"
                + "<head>"
                + "    <meta charset='UTF-8'>"
                + "    <title>User Service v2.0</title>"
                + "</head>"
                + "<body>"
                + sb.toString()
                + "<br>"
                + "<br>"
                + "  <form method='get' action='/create'>"
                + "      <input type='submit' value='CREATE'>"
                + "  </form>"
                + "</body>"
                + "</html>");
        writer.flush();
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        doGet(req, resp);
    }
}
