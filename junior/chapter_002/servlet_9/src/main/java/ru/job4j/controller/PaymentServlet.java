package ru.job4j.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.entity.Place;
import ru.job4j.service.PlaceService;
import ru.job4j.service.PlaceServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class PaymentServlet extends HttpServlet {
    private final static Logger LOG = LogManager.getLogger(PaymentServlet.class);
    private final PlaceService placeService = PlaceServiceImpl.getPlaceServiceInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        req.setCharacterEncoding("UTF-8");
        req.getRequestDispatcher("payment.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        BufferedReader reader = req.getReader();
        StringBuilder sb = new StringBuilder();
        String data;
        while ((data = reader.readLine()) != null) {
            sb.append(data);
        }
        LOG.debug(sb.toString());
        ObjectMapper mapper = new ObjectMapper();
        Place tmp = mapper.readValue(sb.toString(), Place.class);
        Place place = this.placeService.findByParam(tmp);
        LOG.debug("Place with cost: {} ", place.toString());
        String json = mapper.writeValueAsString(place);
        PrintWriter writer = resp.getWriter();
        writer.print(json);
        writer.flush();
    }
}
