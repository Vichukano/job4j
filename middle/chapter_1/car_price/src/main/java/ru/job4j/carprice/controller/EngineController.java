package ru.job4j.carprice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.carprice.model.Engine;
import ru.job4j.carprice.service.EngineService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class EngineController extends HttpServlet {
    private final EngineService service = EngineService.getInstance();
    private final Logger logger = LogManager.getLogger(EngineService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        List<Engine> engines = this.service.findAll();
        String jsonEngines = new ObjectMapper().writeValueAsString(engines);
        logger.debug("Engines in JSON: {}", jsonEngines);
        PrintWriter writer = resp.getWriter();
        writer.print(jsonEngines);
        writer.flush();
    }
}
