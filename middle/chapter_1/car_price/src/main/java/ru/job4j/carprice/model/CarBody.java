package ru.job4j.carprice.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "car_body")
public class CarBody {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "body_id")
    private long id;

    private String type;

    public CarBody() {

    }

    public CarBody(String type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CarBody)) {
            return false;
        }
        CarBody carBody = (CarBody) o;
        return id == carBody.id
                && Objects.equals(type, carBody.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }

    @Override
    public String toString() {
        return String.format("CarBody id = %d, type = %s", id, type);
    }
}
