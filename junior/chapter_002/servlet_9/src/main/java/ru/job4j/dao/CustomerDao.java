package ru.job4j.dao;

import ru.job4j.entity.Customer;
import ru.job4j.entity.Place;

import java.util.List;

/**
 * Dao interface for customers.
 */
public interface CustomerDao {

    boolean addCustomerWithPlace(Customer customer, Place place);

    boolean deleteCustomerWithPlace(int id);

    Customer findById(int id);

    List<Customer> findAll();
}
