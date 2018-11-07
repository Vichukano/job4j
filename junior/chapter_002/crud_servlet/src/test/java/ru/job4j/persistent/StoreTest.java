package ru.job4j.persistent;


import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.job4j.model.User;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class StoreTest {
    private static final Store STORE = MemoryStore.getStoreInstance();

    @BeforeClass
    public static void setUp() {
        STORE.add(new User(
                "test",
                "test",
                "test"
        ));
        STORE.add(new User(
                "test1",
                "test1",
                "test1"
        ));
        STORE.add(new User(
                "test2",
                "test2",
                "test2"
        ));
    }

    @AfterClass
    public static void reset() {
        STORE.delete(1);
        STORE.delete(2);
        STORE.delete(3);
    }

    @Test
    public void whenAddAndDeleteUserSizeShouldChanged() {
        assertThat(STORE.getUsers().size(), is(3));
    }

    @Test
    public void whenUpdateUserItShouldChanged() {
        int id = STORE.getUsers().get(0).getId();
        User updatedUser = new User(
                "updated",
                "updated",
                "updated"
        );
        STORE.update(id, updatedUser);
        assertThat(updatedUser.getId(), is(id));
        assertThat(STORE.getUsers().get(0).getName(), is("updated"));
    }

    @Test
    public void whenFindAllUsersShouldReturnNewArrayList() {
        assertThat(STORE.findAll().get(0).getName(), is("test"));
        assertThat(STORE.findAll().get(1).getName(), is("test1"));
        assertThat(STORE.findAll().get(2).getName(), is("test2"));
    }
}
