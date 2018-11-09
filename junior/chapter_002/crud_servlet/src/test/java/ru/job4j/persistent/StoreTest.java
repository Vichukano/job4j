package ru.job4j.persistent;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import ru.job4j.model.User;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StoreTest {
    private final Store store = MemoryStore.getStoreInstance();


    @Before
    public void resetBefore() {
        store.delete(1);
        store.delete(2);
        store.delete(3);
        store.delete(4);
    }

    @After
    public void resetAfter() {
        store.delete(1);
        store.delete(2);
        store.delete(3);
        store.delete(4);
    }


    @Test
    public void whenUpdateUserItShouldChanged() {
        store.add(new User(
                "test",
                "test",
                "test"
        ));
        store.add(new User(
                "test1",
                "test1",
                "test1"
        ));
        store.add(new User(
                "test2",
                "test2",
                "test2"
        ));
        int id = store.getUsers().get(0).getId();
        User updatedUser = new User(
                "updated",
                "updated",
                "updated"
        );
        store.update(id, updatedUser);
        assertThat(updatedUser.getId(), is(id));
        assertThat(store.getUsers().get(0).getName(), is("updated"));
    }

    @Test
    public void whenFindAllUsersShouldReturnNewArrayList() {
        store.add(new User(
                "test",
                "test",
                "test"
        ));
        store.add(new User(
                "test1",
                "test1",
                "test1"
        ));
        store.add(new User(
                "test2",
                "test2",
                "test2"
        ));
        assertThat(store.findAll().get(0).getName(), is("test"));
        assertThat(store.findAll().get(1).getName(), is("test1"));
        assertThat(store.findAll().get(2).getName(), is("test2"));
    }
}
