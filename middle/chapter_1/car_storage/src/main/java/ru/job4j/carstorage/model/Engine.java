package ru.job4j.carstorage.model;


import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "car_engine")
public class Engine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "engine_id")
    private int id;
    @Column(name = "engine_name")
    private String name;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "engine")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Engine engine = (Engine) o;
        return Objects.equals(name, engine.name)
                && Objects.equals(cars, engine.cars);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cars);
    }
}
