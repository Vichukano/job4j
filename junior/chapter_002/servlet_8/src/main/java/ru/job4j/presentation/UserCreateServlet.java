package ru.job4j.presentation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.logic.Action;
import ru.job4j.logic.Validate;
import ru.job4j.logic.ValidateService;
import ru.job4j.model.City;
import ru.job4j.model.Country;
import ru.job4j.model.Role;
import ru.job4j.model.User;
import ru.job4j.persistent.DbStore;
import ru.job4j.persistent.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet class for adding users.
 */
public class UserCreateServlet extends HttpServlet {
    private final Validate logic = ValidateService.getInstance();
    private final Store store = DbStore.getInstance();
    private final Logger logger = LogManager.getLogger(UserCreateServlet.class);

    /**
     * Method doGet.
     * Redirect to UserCreateServlet when GET method calls from index.jsp.
     * @param req client request.
     * @param resp server response.
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        req.getRequestDispatcher("/WEB-INF/views/create.jsp").forward(req, resp);

    }

    /**
     * doPost method.
     * Get parameters of user name, login and email from create.jsp form.
     * Create user model with this parameters.
     * If name parameter and login parameter is not empty, call method action("ADD", user) from ValidateService.class.
     * After that redirect to UserServlet.
     * @param req client request.
     * @param resp server response.
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        String countryID = req.getParameter("country");
        String cityId = req.getParameter("city");
        String action = req.getParameter("action");
        Action.Type actionType = Action.Type.valueOf(action.toUpperCase());
        if (!req.getParameter("login").trim().equals("") && !req.getParameter("password").trim().equals("")) {
            User user = new User(
                    req.getParameter("login"),
                    req.getParameter("password"),
                    req.getParameter("email")
            );
            Role role = DbStore.getInstance().findRoleByName(req.getParameter("role"));
            Country country = store.getCountryByID(Integer.parseInt(countryID));
            City city = store.getCityById(Integer.parseInt(cityId));
            logger.debug(role.getRoleId() + " " + role.getName());
            user.setRoleId(role.getRoleId());
            user.setRoleName(role.getName());
            user.setCountry(country.getName());
            user.setCity(city.getName());
            logic.init().action(actionType, user);
            resp.sendRedirect("/");
        } else {
            resp.sendRedirect("/create");
        }
    }
}
