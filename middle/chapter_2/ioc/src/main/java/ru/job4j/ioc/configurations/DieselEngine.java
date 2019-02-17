package ru.job4j.ioc.configurations;

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
}
