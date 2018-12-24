package ru.job4j.model;

public class Place {
    private int id;
    private int row;
    private int col;
    private double cost;

    public Place() {}

    public Place(int row, int col) {
        this.row = row;
        this.col = col;
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

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Place{"
                + "row="
                + row
                + ", col="
                + col
                + ", cost= "
                + cost
                + '}';
    }
}
