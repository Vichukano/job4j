package ru.job4j.carstorage.model;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "car_engine")
public class Engine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "engine_id")
    private int id;
    @Column(name = "engine_name")
    private String name;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "engine")
    private List<Car> cars;

    public Engine() {

    }

    public Engine(String name) {
        this.name = name;
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

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    @Override
    public String toString() {
        return "Engine{"
                + "id="
                + id
                + ", name='"
                + name
                + '\''
                + '}';
    }
}
