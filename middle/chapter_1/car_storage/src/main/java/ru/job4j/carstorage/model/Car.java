package ru.job4j.carstorage.model;

import javax.persistence.*;

@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    //@Column(name = "body")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "body_id")
    private CarBody body;
    //@Column(name = "engine")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "engine_id")
    private Engine engine;
    //@Column(name = "transmission")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transmission_id")
    private Transmission transmission;

    public Car() {

    }

    public Car(String name, CarBody body, Engine engine, Transmission transmission) {
        this.name = name;
        this.body = body;
        this.engine = engine;
        this.transmission = transmission;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    public String toString() {
        return "Car{"
                + "id="
                + id
                + ", name='"
                + name
                + '\''
                + ", body="
                + body
                + ", engine="
                + engine
                + ", transmission="
                + transmission
                + '}';
    }
}
