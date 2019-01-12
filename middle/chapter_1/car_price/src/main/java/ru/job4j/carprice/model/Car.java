package ru.job4j.carprice.model;

import java.util.Objects;

public class Car {

    private long id;

    private String name;

    private Double price;

    private CarBody body;

    private Engine engine;

    private Transmission transmission;

    public Car() {

    }

    public Car(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public Car (String name, Double price, CarBody body, Engine engine, Transmission transmission) {
        this.name = name;
        this.price = price;
        this.body = body;
        this.engine = engine;
        this.transmission = transmission;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public CarBody getBody() {
        return body;
    }

    public void setBody(CarBody body) {
        this.body = body;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Car)) {
            return false;
        }
        Car car = (Car) o;
        return id == car.id
                && Objects.equals(name, car.name)
                && Objects.equals(price, car.price)
                && Objects.equals(body, car.body)
                && Objects.equals(engine, car.engine)
                && Objects.equals(transmission, car.transmission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, body, engine, transmission);
    }

    @Override
    public String toString() {
        return "Car "
                + "id="
                + id
                + ", name="
                + name
                +", price="
                + price
                + ", body="
                + body
                + ", engine="
                + engine
                + ", transmission="
                + transmission ;
    }
}
