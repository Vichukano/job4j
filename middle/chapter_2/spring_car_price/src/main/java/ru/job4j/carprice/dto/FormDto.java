package ru.job4j.carprice.dto;

/**
 * Class of DTO, that contains request params
 * from update car form in update.html page.
 */
public class FormDto {
    private int carId;
    private int mileage;
    private long body;
    private long engine;
    private long transmission;
    private double price;
    private String name;
    private String color;
    private String desc;
    /**
     * Default value for sold param if
     * checkbox is not checked.
     */
    private String sold = "on sale";

    public FormDto() {

    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public long getBody() {
        return body;
    }

    public void setBody(long body) {
        this.body = body;
    }

    public long getEngine() {
        return engine;
    }

    public void setEngine(long engine) {
        this.engine = engine;
    }

    public long getTransmission() {
        return transmission;
    }

    public void setTransmission(long transmission) {
        this.transmission = transmission;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSold() {
        return sold;
    }

    public void setSold(String sold) {
        this.sold = sold;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
