package ru.job4j.carprice.model;

import java.util.Objects;

public class CarBody {

    private long id;

    private String type;

    private String color;

    public CarBody() {

    }

    public CarBody(String type, String color) {
        this.type = type;
        this.color = color;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CarBody)) {
            return false;
        }
        CarBody carBody = (CarBody) o;
        return id == carBody.id
                && Objects.equals(type, carBody.type)
                && Objects.equals(color, carBody.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, color);
    }

    @Override
    public String toString() {
        return String.format("CarBody id = %d, type = %s, color = %s", id, type, color);
    }
}
