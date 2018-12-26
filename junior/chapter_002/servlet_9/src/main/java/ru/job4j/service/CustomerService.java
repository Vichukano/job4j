package ru.job4j.service;

import ru.job4j.entity.Customer;

import java.util.List;

public interface CustomerService {

    boolean add(Customer customer);

    boolean delete(Customer customer);

    Customer findById(Customer customer);

    List<Customer> findAll();
}
