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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Servlet class for main page index.html.
 */
public class HallServlet extends HttpServlet {
    private final PlaceService placeService = PlaceServiceImpl.getPlaceServiceInstance();
    private final static Logger LOG = LogManager.getLogger(HallServlet.class);

    /**
     * Get method.
     * Find all reserved places in database and send them in JSON to client.
     * @param req client request.
     * @param resp server response.
     * @throws ServletException
     * @throws IOException
     */
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
}
