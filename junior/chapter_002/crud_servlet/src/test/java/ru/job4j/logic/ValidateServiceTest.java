package ru.job4j.logic;


import org.junit.AfterClass;
import org.junit.Test;
import ru.job4j.model.User;


import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ValidateServiceTest {
    private static final Validate SERVICE = ValidateService.getInstance();

    @Test
    public void whenAddSameUserShouldReturnFalse() {
        User user = new User("test", "test", "test");
        User sameUser = new User("test", "test", "test");
        SERVICE.add(user);
        sameUser.setId(user.getId());
        assertThat(SERVICE.add(sameUser), is(false));
        assertThat(SERVICE.getUsers().size(), is(1));
    }

    @Test
    public void whenUpdateUserWithIncorrectIdShouldReturnFalse() {
        assertThat(SERVICE.update(10, new User("test", "test", "test")), is(false));
    }

    @Test
    public void whenUpdateUserWithCorrectIdShouldReturnTrue() {
        assertThat(SERVICE.update(1, new User("name", "login", "email")), is(true));
        assertThat(SERVICE.findById(1).getName(), is("name"));
    }

    @AfterClass
    public static void reset() {
        SERVICE.delete(1);
        assertThat(SERVICE.getUsers().size(), is(0));
    }


}
