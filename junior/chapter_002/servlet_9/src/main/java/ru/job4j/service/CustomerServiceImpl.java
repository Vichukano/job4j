package ru.job4j.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.dao.CustomerDao;
import ru.job4j.entity.Customer;
import ru.job4j.persistence.CustomerRepository;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    private final static CustomerService INSTANCE = new CustomerServiceImpl();
    private final CustomerDao customerStore = CustomerRepository.getCustomerStoreInstance();
    private final static Logger LOG = LogManager.getLogger(CustomerServiceImpl.class);

    private CustomerServiceImpl() {

    }

    public static CustomerService getCustomerServiceInstance() {
        return INSTANCE;
    }

    @Override
    public boolean add(Customer customer) {
        boolean result = this.customerStore.add(customer);
        if (!result) {
            LOG.debug("Failed to add customer: {}", customer.toString());
        }
        return result;
    }

    @Override
    public boolean delete(Customer customer) {
        boolean result = this.customerStore.delete(customer.getId());
        if (!result) {
            LOG.debug("Customer with id {} is missing in database.", customer.getId());
        }
        return result;
    }

    @Override
    public Customer findById(Customer customer) {
        return this.customerStore.findById(customer.getId());
    }

    @Override
    public List<Customer> findAll() {
        return this.customerStore.findAll();
    }
}
