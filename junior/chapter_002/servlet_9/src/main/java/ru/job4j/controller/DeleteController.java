package ru.job4j.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.model.Customer;
import ru.job4j.model.Place;
import ru.job4j.persistence.CustomerRepository;
import ru.job4j.persistence.PlaceRepository;
import ru.job4j.persistence.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class DeleteController extends HttpServlet {
    private final Logger logger = LogManager.getLogger(DeleteController.class);
    private final Store<Customer> customerStore = new CustomerRepository();
    private final Store<Place> placeStore = new PlaceRepository();

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
        logger.debug(sb.toString());
        int id = Integer.parseInt(sb.toString());
        Customer customer = this.customerStore.findById(id);
        Place place = this.placeStore.findById(customer.getPlaceId());
        place.setReserved(false);
        this.placeStore.updatePlace(place);
        this.customerStore.delete(id);
        PrintWriter writer = resp.getWriter();
        writer.print("OK");
        writer.flush();
    }
}
