package ru.job4j.carprice.model;

import java.util.Objects;

public class Engine {

    private long id;

    private String type;

    public Engine() {

    }

    public Engine(String type) {
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
        if (!(o instanceof Engine)) {
            return false;
        }
        Engine engine = (Engine) o;
        return id == engine.id
                && Objects.equals(type, engine.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }

    @Override
    public String toString() {
        return String.format("Engine id = %d, type = %s", id, type);
    }
}
