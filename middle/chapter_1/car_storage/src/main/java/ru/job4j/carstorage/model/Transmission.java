package ru.job4j.carstorage.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "transmission")
public class Transmission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transmission_id")
    private int id;
    @Column(name = "transmission_name")
    private String name;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "transmission")
    private List<Car> cars;

    public Transmission() {

    }

    public Transmission(String name) {
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
        return "Transmission{"
                + "id="
                + id
                + ", name='"
                + name
                + '\''
                + '}';
    }
}
