package ru.job4j.service;

import ru.job4j.entity.Customer;
import ru.job4j.entity.Place;

import java.util.List;

/**
 * Interface for service methods with customer and place objects.
 */
public interface CustomerService {

    boolean addCustomerWithPlace(Customer customer, Place place);

    boolean deleteCustomerWithPlace(Customer customer);

    Customer findById(Customer customer);

    List<Customer> findAll();
}
