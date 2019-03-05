package ru.job4j.carprice.persistence.implementation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.job4j.carprice.configuration.Config;
import ru.job4j.carprice.configuration.WebbAppInit;
import ru.job4j.carprice.model.User;
import ru.job4j.carprice.persistence.UserDao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Config.class, WebbAppInit.class})
@WebAppConfiguration
public class UserDaoImplTest {

    @Autowired
    private UserDao dao;

    @Test
    public void whenFindAllThenReturnListOfUsers() {
        assertNotNull(this.dao.findAll());
        assertThat(this.dao.findAll().get(0).getLogin(), is("root"));
    }

    @Test
    public void whenFindUserByLoginThenReturnUserWithThisLogin() {
        User user = new User("root", "root");
        assertThat(this.dao.findByParam(user).getLogin(), is("root"));
    }

}
