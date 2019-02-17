package ru.job4j.ioc.annotations.beans;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Qualifier("diesel")
public class DieselEngine implements Engine {
    private String name;

    public DieselEngine() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @PostConstruct
    private void init() {
        this.setName("DIESEL");
    }
}
