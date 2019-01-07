package ru.job4j.todolist.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Entity-class of Item.
 * Table name - items.
 */
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "item_desc")
    private String desc;
    @Column(name = "created")
    private Timestamp created;
    @Column(name = "done")
    private boolean done;

    public Item() {

    }

    public Item(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = new Timestamp(created.getTime());
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", desc='" + desc + '\'' +
                ", created=" + created +
                ", done=" + done +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Item)) {
            return false;
        }
        Item item = (Item) o;
        return id == item.id
                && done == item.done
                && Objects.equals(desc, item.desc)
                && Objects.equals(created, item.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, desc, created, done);
    }
}
