package ru.job4j.storage;

import java.util.Objects;

public class User {
    private String firstname;
    private String lastname;

    public User() {

    }

    public User(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return String.format("User: "
                + "firstname = %s, "
                + "lastname = %s", firstname, lastname);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(firstname, user.firstname)
                && Objects.equals(lastname, user.lastname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstname, lastname);
    }
}
