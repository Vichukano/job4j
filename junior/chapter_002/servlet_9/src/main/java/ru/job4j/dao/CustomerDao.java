package ru.job4j.dao;

import ru.job4j.entity.Customer;

import java.util.List;

public interface CustomerDao {

    boolean add(Customer customer);

    boolean delete(int id);

    Customer findById(int id);

    List<Customer> findAll();
}
