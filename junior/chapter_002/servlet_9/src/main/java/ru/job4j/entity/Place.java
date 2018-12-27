package ru.job4j.entity;

import java.util.Objects;

/**
 * Place entity class.
 */
public class Place {
    private int id;
    private int row;
    private int col;
    private double cost;
    private boolean reserved = false;

    public Place() {

    }

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

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    @Override
    public String toString() {
        return "Place{"
                + "id="
                + id
                + ", row="
                + row
                + ", col="
                + col
                + ", cost= "
                + cost
                + ", reserved: "
                + reserved
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Place)) {
            return false;
        }
        Place place = (Place) o;
        return id == place.id
                && row == place.row
                && col == place.col
                && Double.compare(place.cost, cost) == 0
                && reserved == place.reserved;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, row, col, cost, reserved);
    }
}
