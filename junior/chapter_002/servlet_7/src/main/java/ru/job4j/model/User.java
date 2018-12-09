package ru.job4j.model;

import java.util.Objects;

/**
 * User model class.
 * Extends abstract class model.
 */
public class User extends Model {
    private String surname;
    private String sex;
    private String desc;

    public User() {
        super();
    }

    public User(String name, String surname, String sex, String desc) {
        super(name);
        this.surname = surname;
        this.sex = sex;
        this.desc = desc;
    }

    @Override
    public int getId() {
        return super.getId();
    }

    @Override
    public void setId(int id) {
        super.setId(id);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "id: "
                + getId()
                + " name: "
                + getName()
                + " surname: "
                + surname
                + " sex: "
                + sex
                + " desc: "
                + desc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(surname, user.surname)
                && Objects.equals(sex, user.sex)
                && Objects.equals(desc, user.desc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), surname, sex, desc);
    }
}
