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

/**
 * Servlet class for payment.html page.
 */
public class PaymentServlet extends HttpServlet {
    private final static Logger LOG = LogManager.getLogger(PaymentServlet.class);
    private final PlaceService placeService = PlaceServiceImpl.getPlaceServiceInstance();

    /**
     * Get method.
     * Redirect to payment.html page.
     * @param req client request.
     * @param resp server response.
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("payment.html").forward(req, resp);
    }

    /**
     * Post method.
     * Read JSON data from client with row and col parameters.
     * Build and find place object with this parameters from database.
     * Send this place object to client in JSON format.
     * @param req client request.
     * @param resp server response.
     * @throws ServletException
     * @throws IOException
     */
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
