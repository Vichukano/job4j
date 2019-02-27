package ru.job4j.ioc.xml;

import java.util.Objects;

public class GasolineEngine implements Engine {
    private String name;

    public GasolineEngine() {

    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private void init() {
        this.setName("GASOLINE");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GasolineEngine that = (GasolineEngine) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return String.format("GasolineEngine: name = %s", name);
    }
}
