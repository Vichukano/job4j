package ru.job4j.controller;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import ru.job4j.entity.Customer;
import ru.job4j.entity.Place;
import ru.job4j.service.CustomerService;

import java.util.Arrays;
import java.util.List;

public class CustomerServiceImplStub implements CustomerService {
    @Override
    public boolean addCustomerWithPlace(Customer customer, Place place) {
        String cust = String.format("Customer with id = %d added", customer.getId());
        String pls = String.format("Place with id = %d added", place.getId());
        System.out.println(cust);
        System.out.println(pls);
        return true;
    }

    @Override
    public boolean deleteCustomerWithPlace(Customer customer) {
        String result = String.format("Customer with id = %d deleted", customer.getId());
        System.out.print(result);
        return true;
    }

    @Override
    public Customer findById(Customer customer) {
        return null;
    }

    @Override
    public List<Customer> findAll() {
        return Arrays.asList(
                new Customer("test", "test"),
                new Customer("test", "test"),
                new Customer("test", "test")
        );
    }
}
