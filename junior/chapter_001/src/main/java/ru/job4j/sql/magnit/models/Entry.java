package ru.job4j.sql.magnit.models;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


import java.util.List;

@XmlRootElement(name = "entries")
public class Entry {
    private List<Field> values;

    public Entry() {
    }

    public Entry(List<Field> values) {
        this.values = values;
    }
    @XmlElement(name = "entry")
    public List<Field> getValues() {
        return values;
    }

    public void setValues(List<Field> values) {
        this.values = values;
    }
}