package ru.job4j.controller;

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

public class DeleteController extends HttpServlet {
    private final static Logger LOG = LogManager.getLogger(DeleteController.class);
    private final CustomerService customerService = CustomerServiceImpl.getCustomerServiceInstance();
    private final PlaceService placeService = PlaceServiceImpl.getPlaceServiceInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        BufferedReader reader = req.getReader();
        StringBuilder sb = new StringBuilder();
        String data;
        while ((data = reader.readLine()) != null) {
            sb.append(data);
        }
        LOG.debug(sb.toString());
        Customer customerForDelete = new Customer();
        customerForDelete.setId(Integer.parseInt(sb.toString()));
        Customer customer = this.customerService.findById(customerForDelete);
        Place placeForFind = new Place();
        placeForFind.setId(customer.getPlaceId());
        Place place = this.placeService.findById(placeForFind);
        place.setReserved(false);
        LOG.debug(place.toString());
        this.placeService.updatePlace(place);
        this.customerService.delete(customer);
    }
}
