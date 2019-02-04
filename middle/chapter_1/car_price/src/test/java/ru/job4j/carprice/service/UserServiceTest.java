package ru.job4j.carprice.service;

import org.junit.Ignore;
import org.junit.Test;
import ru.job4j.carprice.model.User;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UserServiceTest {
    private final UserService service = UserService.getInstance();

    @Ignore
    @Test
    public void whenFindByLoginThenReturnUserWithThisLogin() {
        User user = this.service.findByLogin("first");
        assertThat(user.getPassword(), is("123"));
        assertThat(user.getLogin(), is("first"));
    }

    @Ignore
    @Test
    public void whenFindByIdThenReturnUserWithThisId() {
        User user = this.service.findByLogin("first");
        User foundById = this.service.findById(user.getId());
        assertThat(foundById.getPassword(), is("123"));
        assertThat(foundById.getLogin(), is("first"));
    }

    @Ignore
    @Test
    public void whenCredentialThenReturnTrue() {
        assertThat(this.service.isCredential("first", "123"), is(true));
    }

    @Ignore
    @Test
    public void whenNotCredentialThenReturnFalse() {
        assertThat(this.service.isCredential("first", "321"), is(false));
        assertThat(this.service.isCredential("test", "123"), is(false));

    }

    @Ignore
    @Test
    public void whenExistThenReturnTrue() {
        User user = new User();
        user.setLogin("first");
        assertThat(this.service.isExist(user), is(true));
    }

    @Ignore
    @Test
    public void whenNotExistThenReturnTrue() {
        User user = new User();
        user.setLogin("test");
        assertThat(this.service.isExist(user), is(false));
    }
}
