package ru.job4j.carprice.model;

import java.util.Objects;

public class Transmission {

    private long id;

    private String type;

    public Transmission() {

    }

    public Transmission(String type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transmission)) {
            return false;
        }
        Transmission that = (Transmission) o;
        return id == that.id
                && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }

    @Override
    public String toString() {
        return String.format("Transmission id = %d, type = %s", id, type );
    }
}
