package ru.job4j.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.entity.Customer;
import ru.job4j.entity.Place;
import ru.job4j.service.CustomerService;
import ru.job4j.service.CustomerServiceImpl;
import ru.job4j.service.PlaceService;
import ru.job4j.service.PlaceServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public class OrderController extends HttpServlet {
    private final static Logger LOG = LogManager.getLogger(OrderController.class);
    private final CustomerService customerService = CustomerServiceImpl.getCustomerServiceInstance();
    private final PlaceService placeService = PlaceServiceImpl.getPlaceServiceInstance();

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
        Place place = new Place();
        place.setId(customer.getPlaceId());
        Place reservedPlace = this.placeService.findById(place);
        reservedPlace.setReserved(true);
        customer.setRow(reservedPlace.getRow());
        customer.setCol(reservedPlace.getCol());
        this.placeService.updatePlace(reservedPlace);
        this.customerService.add(customer);
        LOG.debug("{} added", customer.toString());
    }
}
