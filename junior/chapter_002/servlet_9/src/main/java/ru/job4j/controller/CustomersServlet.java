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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CustomersServlet extends HttpServlet {
    private final Logger logger = LogManager.getLogger(CustomersServlet.class);
    private final Store<Customer> customerStore = new CustomerRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        req.getRequestDispatcher("customers.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        List<Customer> customers = this.customerStore.findAll();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(customers);
        logger.debug(json);
        PrintWriter writer = resp.getWriter();
        writer.print(json);
        writer.flush();
    }

}
