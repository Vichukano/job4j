package ru.job4j.logic;


import org.junit.Test;
import ru.job4j.model.User;


import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ValidateServiceTest {
    private final ValidateService validate = ValidateService.getInstance();

    @Test
    public void whenAddSameUserShouldReturnFalse() {
        User user = new User("test", "test", "test");
        User sameUser = new User("test", "test", "test");
        String addAction = "ADD";
        Action.Type action = Action.Type.valueOf(addAction);
        validate.init().action(action, user);
        sameUser.setId(user.getId());
        assertThat(validate.init().action(action, sameUser), is(false));
        assertThat(validate.getUsers().size(), is(1));
    }

    @Test
    public void whenUpdateUserWithIncorrectIdShouldReturnFalse() {
        String updateAction = "UPDATE";
        Action.Type action = Action.Type.valueOf(updateAction);
        assertThat(validate.init().action(action, new User("test", "test", "test")), is(false));
    }

    @Test
    public void whenUpdateUserWithCorrectIdShouldReturnTrue() {
        Action.Type update = Action.Type.valueOf("UPDATE");
        Action.Type add = Action.Type.valueOf("ADD");
        User user = new User("forUpdate", "forUpdate", "forUpdate");
        User updatedUser = new User("farazenda", "farazenda", "farazenda");
        updatedUser.setId(user.getId());
        validate.init().action(add, user);
        assertThat(validate.init().action(update, updatedUser), is(true));
        assertThat(validate.findById(updatedUser.getId()).getName(), is("farazenda"));
    }
}
