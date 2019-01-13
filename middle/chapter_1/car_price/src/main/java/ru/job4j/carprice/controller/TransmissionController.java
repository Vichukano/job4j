package ru.job4j.carprice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.carprice.model.Transmission;
import ru.job4j.carprice.service.TransmissionService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class TransmissionController extends HttpServlet {
    private final TransmissionService service = TransmissionService.getInstance();
    private final Logger logger = LogManager.getLogger(TransmissionService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        List<Transmission> transmissions = this.service.findAll();
        String jsonTransmissions = new ObjectMapper().writeValueAsString(transmissions);
        logger.debug("Transmission in JSON: {}", jsonTransmissions);
        PrintWriter writer = resp.getWriter();
        writer.print(jsonTransmissions);
        writer.flush();
    }
}
