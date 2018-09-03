package ru.job4j.collections.store;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class StoreTest {
    private List<Store.User> pre;
    private List<Store.User> curr;


    @Before
    public void setUp() {
        this.pre = new ArrayList<>();
        pre.add(new Store.User(1, "First"));
        pre.add(new Store.User(2, "Second"));
        pre.add(new Store.User(3, "Third"));
        pre.add(new Store.User(4, "Four"));
        pre.add(new Store.User(5, "Five"));
        pre.add(new Store.User(6, "Six"));
        this.curr = new ArrayList<>();
        curr.addAll(pre);
        //Add 3
        curr.add(new Store.User(7, "Seven"));
        curr.add(new Store.User(8, "Eight"));
        curr.add(new Store.User(9, "Nine"));
        //Delete 2
        curr.remove(0);
        curr.remove(0);
        //Change 2
        curr.set(0, new Store.User(3, "Foo"));
        curr.set(2, new Store.User(5, "Bar"));
    }

    @Test
    public void whenDiffCollectionsShouldReturnCorrectValue() {
        Store store = new Store();
        assertThat(store.diff(pre, curr), is("Added: " + 3 + " " + "Deleted: " + 2 + " " + "Changed: " + 2));
    }
}
