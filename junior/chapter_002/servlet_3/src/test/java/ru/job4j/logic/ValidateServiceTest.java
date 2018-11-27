package ru.job4j.logic;

import org.junit.AfterClass;
import org.junit.Test;
import ru.job4j.model.User;

import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ValidateServiceTest {
    private static final ValidateService SERVICE = ValidateService.getInstance();

    @AfterClass
    public static void reset() {
        User user = new User("test", "test", "test");
        User user1 = new User("test1", "test1", "test1");
        SERVICE.init().action(Action.Type.valueOf("DELETE"), user);
        SERVICE.init().action(Action.Type.valueOf("DELETE"), user1);
        SERVICE.init().action(Action.Type.valueOf("DELETE"), user);
    }

    @Test
    public void whenAddUserThenAddItToCollection() {
        User user = new User("test", "test", "test");
        User user1 = new User("test1", "test1", "test1");
        assertThat(SERVICE.init().action(Action.Type.valueOf("ADD"), user), is(true));
        assertThat(SERVICE.init().action(Action.Type.valueOf("ADD"), user1), is(true));
        SERVICE.init().action(Action.Type.valueOf("ADD"), user);
        assertThat(SERVICE.init().action(Action.Type.valueOf("ADD"), user), is(false));
        assertThat(SERVICE.getUsers(), is(Arrays.asList(user, user1)));
    }

    @Test
    public void whenUpdateUserThanUpdateItOrReturnFalse() {
        User user = new User("UPDATED", "test", "test");
        user.setId(1);
        assertThat(SERVICE.init().action(Action.Type.valueOf("UPDATE"), user), is(true));
        assertThat(SERVICE.findById(1).getName(), is("UPDATED"));
    }

}
