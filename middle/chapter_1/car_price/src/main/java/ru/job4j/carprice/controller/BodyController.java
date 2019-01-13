package ru.job4j.carprice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.carprice.model.CarBody;
import ru.job4j.carprice.service.CarBodyService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class BodyController extends HttpServlet {
    private final CarBodyService service = CarBodyService.getInstance();
    private final Logger logger = LogManager.getLogger(BodyController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        List<CarBody> bodies = this.service.findAll();
        String jsonBodies = new ObjectMapper().writeValueAsString(bodies);
        logger.debug("Bodies in JSON: {}", jsonBodies);
        PrintWriter writer = resp.getWriter();
        writer.print(jsonBodies);
        writer.flush();
    }
}
