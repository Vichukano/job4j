package ru.job4j.carprice.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private Double price;

    @Column(name = "color")
    private String color;

    @Column(name = "mileage")
    private int mileage;

    @Column(name = "sold")
    private boolean sold;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "body_id")
    private CarBody body;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "engine_id")
    private Engine engine;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "transmission_id")
    private Transmission transmission;

    @OneToOne(
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "image_id")
    private Image image;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    public Car() {

    }

    public Car(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public Car(String name, Double price, String color, CarBody body,
               Engine engine, Transmission transmission, int mileage,
               String description, User user) {
        this.name = name;
        this.price = price;
        this.color = color;
        this.body = body;
        this.engine = engine;
        this.transmission = transmission;
        this.mileage = mileage;
        this.description = description;
        this.user = user;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
                && mileage == car.mileage
                && sold == car.sold
                && Objects.equals(name, car.name)
                && Objects.equals(price, car.price)
                && Objects.equals(color, car.color)
                && Objects.equals(description, car.description)
                && Objects.equals(body, car.body)
                && Objects.equals(engine, car.engine)
                && Objects.equals(transmission, car.transmission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, color, mileage, sold, description, body, engine, transmission);
    }

    @Override
    public String toString() {
        return "Car "
                + "id="
                + id
                + ", name="
                + name
                + ", price="
                + price
                + ", body="
                + body
                + ", engine="
                + engine
                + ", transmission="
                + transmission
                + ", color="
                + color
                + ", mileage="
                + mileage
                + ", sold="
                + sold
                + ", description="
                + description
                + ", image="
                + image
                + "' user="
                + user;
    }
}
