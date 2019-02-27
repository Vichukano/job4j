package ru.job4j.ioc.xml;

import java.util.Objects;

public class DieselEngine implements Engine {
    private String name;

    public DieselEngine() {

    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DieselEngine that = (DieselEngine) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return String.format("DieselEngine: name = %s", name);
    }
}
