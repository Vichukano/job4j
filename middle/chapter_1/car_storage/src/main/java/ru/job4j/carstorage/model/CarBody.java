package ru.job4j.carstorage.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "car_body")
public class CarBody {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "body_id")
    private int id;
    @Column(name = "body_name")
    private String name;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "body")
    private List<Car> cars;

    public CarBody() {

    }

    public CarBody(String name) {
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
        return "CarBody{"
                + "id="
                + id
                + ", name='"
                + name
                + '\''
                + '}';
    }
}
