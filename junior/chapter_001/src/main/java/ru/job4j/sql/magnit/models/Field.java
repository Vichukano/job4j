package ru.job4j.sql.magnit.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


public class Field {
    private int value;

    public Field() {

    }

    public Field(int value) {
        this.value = value;
    }

    @XmlElement(name = "field")
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
