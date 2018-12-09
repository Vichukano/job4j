package ru.job4j.persistent;

import org.junit.Test;
import ru.job4j.model.User;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MemoryStoreTest {
    private final Store store = MemoryStore.getInstance();

    @Test
    public void whenAddUserThenStoreIt() {
        User user = new User("test", "test", "test", "test");
        this.store.add(user);
        assertThat(this.store.findAll().size(), is(1));
        User testUser = (User) this.store.findAll().get(0);
        assertThat(testUser.getName(), is("test"));
        this.store.delete(user.getId());
        assertThat(MemoryStore.getInstance().findAll().size(), is(0));
    }
}
