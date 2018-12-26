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
import java.util.List;

public class HallServlet extends HttpServlet {
    private final PlaceService placeService = PlaceServiceImpl.getPlaceServiceInstance();
    private final static Logger LOG = LogManager.getLogger(HallServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        List<Place> places = this.placeService.findAllReserved();
        LOG.debug(places);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(places);
        LOG.debug(json);
        PrintWriter writer = resp.getWriter();
        writer.print(json);
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        BufferedReader reader = req.getReader();
        StringBuilder sb = new StringBuilder();
        String data;
        while ((data = reader.readLine()) != null) {
            sb.append(data);
        }
        LOG.debug(sb.toString());
        ObjectMapper mapper = new ObjectMapper();
        Place place = mapper.readValue(sb.toString(), Place.class);
        LOG.debug(place.toString());
        this.placeService.add(place);
        String json = mapper.writeValueAsString(place);
        PrintWriter writer = resp.getWriter();
        writer.print(json);
        writer.flush();
    }
}
