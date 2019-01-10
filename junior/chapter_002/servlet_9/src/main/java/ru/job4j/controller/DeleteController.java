package ru.job4j.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.entity.Customer;
import ru.job4j.service.CustomerService;
import ru.job4j.service.CustomerServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Servlet class for deleting customers.
 */
public class DeleteController extends HttpServlet {
    private final static Logger LOG = LogManager.getLogger(DeleteController.class);
    private final CustomerService customerService = CustomerServiceImpl.getCustomerServiceInstance();

    /**
     * Post method.
     * Read id from client side.
     * Delete customer with this id from database.
     * @param req client request.
     * @param resp server response.
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        StringBuilder sb = new StringBuilder();
        String data;
        while ((data = reader.readLine()) != null) {
            sb.append(data);
        }
        LOG.debug(sb.toString());
        Customer customerForDelete = new Customer();
        customerForDelete.setId(Integer.parseInt(sb.toString()));
        this.customerService.deleteCustomerWithPlace(customerForDelete);
    }
}
