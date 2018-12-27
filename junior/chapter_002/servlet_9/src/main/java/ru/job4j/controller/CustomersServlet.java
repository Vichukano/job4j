package ru.job4j.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.entity.Customer;
import ru.job4j.service.CustomerService;
import ru.job4j.service.CustomerServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Servlet class for customer list page.
 */
public class CustomersServlet extends HttpServlet {
    private final static Logger LOG = LogManager.getLogger(CustomersServlet.class);
    private final CustomerService customerService = CustomerServiceImpl.getCustomerServiceInstance();

    /**
     * Get method redirect to customers.html page.
     * @param req client request.
     * @param resp server response.
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        req.getRequestDispatcher("customers.html").forward(req, resp);
    }

    /**
     * Post method.
     * Send list of customers in JSON to client.
     * @param req client request.
     * @param resp server response.
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        List<Customer> customers = this.customerService.findAll();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(customers);
        LOG.debug(json);
        PrintWriter writer = resp.getWriter();
        writer.print(json);
        writer.flush();
    }

}
