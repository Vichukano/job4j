package ru.job4j.presentation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.logic.Action;
import ru.job4j.logic.Validate;
import ru.job4j.logic.ValidateService;
import ru.job4j.model.City;
import ru.job4j.model.Country;
import ru.job4j.model.User;
import ru.job4j.persistent.DbStore;

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
    private final static Logger LOG = LogManager.getLogger(RegistrationServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        req.getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String confirm = req.getParameter("confirm");
        String email = req.getParameter("email");
        String countryID = req.getParameter("country");
        String cityId = req.getParameter("city");
        String action = req.getParameter("action");
        Action.Type actionType = Action.Type.valueOf(action.toUpperCase());
        if (!req.getParameter("login").trim().equals("") && !req.getParameter("password").trim().equals("")) {
            if (password.equals(confirm)) {
                if (!this.service.isExist(login)) {
                    User user = new User(login, password, email);
                    Country country = DbStore.getInstance().getCountryByID(Integer.parseInt(countryID));
                    City city = DbStore.getInstance().getCityById(Integer.parseInt(cityId));
                    user.setRoleId(2);
                    user.setCountry(country.getName());
                    user.setCity(city.getName());
                    this.service.init().action(actionType, user);
                    user = this.service.findByLogin(login);
                    LOG.debug(user.toString());
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
