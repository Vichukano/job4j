package ru.job4j.carprice.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.job4j.carprice.configuration.Config;
import ru.job4j.carprice.model.User;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Config.class})
@WebAppConfiguration
public class UserServiceTest {

    @Autowired
    private UserService service;

    @Test
    public void whenFindByIdThenReturnUser() {
        assertThat(this.service.findById(1L).getLogin(), is("root"));
        assertThat(this.service.findById(1L).getPassword(), is("root"));
    }

    @Test
    public void whenFindByLoginThenReturnUserWithThisLogin() {
        assertThat(this.service.findByLogin("root").getLogin(), is("root"));
    }

    @Test
    public void whenUserIsCredentialThenReturnTrue() {
        assertTrue(this.service.isCredential("root", "root"));
    }

    @Test
    public void whenUserNotCredentialThenReturnFalse() {
        assertFalse(this.service.isCredential("test", "root"));
        assertFalse(this.service.isCredential("test", "test"));
        assertFalse(this.service.isCredential("root", "test"));
    }

    @Test
    public void whenUserIsExistThenReturnTrue() {
        User root = new User("root", "root");
        assertTrue(this.service.isExist(root));
    }

    @Test
    public void whenUserNotExistThenReturnFalse() {
        User test = new User("test", "root");
        assertFalse(this.service.isExist(test));
    }


}
