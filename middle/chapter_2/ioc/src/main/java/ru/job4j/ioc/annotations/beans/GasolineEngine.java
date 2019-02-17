package ru.job4j.ioc.annotations.beans;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("gasoline")
public class GasolineEngine implements Engine {
    private String name;

    public GasolineEngine() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
