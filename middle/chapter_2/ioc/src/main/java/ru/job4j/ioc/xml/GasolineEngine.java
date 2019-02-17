package ru.job4j.ioc.xml;

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
}
