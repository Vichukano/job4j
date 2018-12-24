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

public class OrderController extends HttpServlet {
    private final Logger logger = LogManager.getLogger(OrderController.class);
    private final Store<Customer> customerStore = new CustomerRepository();
    private final Store<Place> placeStore = new PlaceRepository();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        StringBuilder sb = new StringBuilder();
        String data;
        while ((data = reader.readLine()) != null) {
            sb.append(data);
        }
        ObjectMapper mapper = new ObjectMapper();
        Customer customer = mapper.readValue(sb.toString(), Customer.class);
        Place reservedPlace = this.placeStore.findById(customer.getPlaceId());
        reservedPlace.setReserved(true);
        customer.setRow(reservedPlace.getRow());
        customer.setCol(reservedPlace.getCol());
        this.placeStore.updatePlace(reservedPlace);
        this.customerStore.add(customer);
        logger.debug("Customer {} added", customer.toString());
    }
}
