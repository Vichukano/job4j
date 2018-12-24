package ru.job4j.model;

public class Customer {
    private int id;
    private String name;
    private String phone;
    private int placeId;
    private int row;
    private int col;

    public Customer() {}

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
                + "name='"
                + name
                + ", phone='"
                + phone
                + ", placeId="
                + placeId
                + ", row= "
                + row
                + ", col="
                + col
                + '}';
    }
}
