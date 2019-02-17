package ru.job4j.storage;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class JDBCApplicationTest {

    @Test
    public void jdbcTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("config.xml");
        ImportUser storage = ctx.getBean(ImportUser.class);
        assertNotNull(storage);
    }

    @Test
    public void memoryTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("config_mock.xml");
        ImportUser storage = ctx.getBean(ImportUser.class);
        assertNotNull(storage);
        User user = new User("test", "test");
        storage.add(user);
        assertThat(storage.findAll().size(), is(1));
        assertThat(storage.findAll().get(0), is(user));
        assertThat(storage.findAll().get(0).getFirstname(), is("test"));
    }
}
