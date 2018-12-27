package ru.job4j.controller;

import ru.job4j.entity.Customer;
import ru.job4j.entity.Place;
import ru.job4j.service.CustomerService;

import java.util.Arrays;
import java.util.List;

public class CustomerServiceImplStub implements CustomerService {
    @Override
    public boolean addCustomerWithPlace(Customer customer, Place place) {
        return false;
    }

    @Override
    public boolean deleteCustomerWithPlace(Customer customer) {
        return false;
    }

    @Override
    public Customer findById(Customer customer) {
        return null;
    }

    @Override
    public List<Customer> findAll() {
        return Arrays.asList(
                new Customer("test", "test" ),
                new Customer("test", "test" ),
                new Customer("test", "test" )
        );
    }
}
