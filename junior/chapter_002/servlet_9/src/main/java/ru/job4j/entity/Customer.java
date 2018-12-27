package ru.job4j.entity;

import java.util.Objects;

/**
 * Customer entity class.
 */
public class Customer {
    private int id;
    private String name;
    private String phone;
    private int placeId;
    private int row;
    private int col;

    public Customer() {

    }

    public Customer(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    @Override
    public String toString() {
        return "Customer{"
                + "id="
                + id
                + ", name="
                + name
                + ", phone="
                + phone
                + ", placeId="
                + placeId
                + ", row="
                + row
                + ", col="
                + col
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        Customer customer = (Customer) o;
        return id == customer.id
                && placeId == customer.placeId
                && row == customer.row
                && col == customer.col
                && Objects.equals(name, customer.name)
                && Objects.equals(phone, customer.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, phone, placeId, row, col);
    }
}
