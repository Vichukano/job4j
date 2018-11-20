package ru.job4j.logic;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ru.job4j.model.User;
import ru.job4j.persistent.DbStoreTest;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ValidateServiceTest {
    private final ValidateService validate = ValidateService.getInstance();
    private final Connector connector = new Connector();

    @Before
    public void setUp() {
        this.connector.createTable();
    }

    @After
    public void reset() {
        this.connector.dropTable();
    }

    @Test
    public void whenAddSameUserTwiceShouldReturnFalse() {
        User user = new User("test", "test", "test");
        user.setId(1);
        User sameUser = new User("test", "test", "test");
        sameUser.setId(user.getId());
        validate.init().action(Action.Type.valueOf("ADD"), user);
        assertThat(validate.init().action(Action.Type.valueOf("ADD"), sameUser), is(false));
    }

    @Test
    public void whenDeleteIncorrectUserShouldReturnFalse() {
        assertThat(validate.init().action(Action.Type.valueOf("DELETE"), new User(
                "test",
                "test",
                "test"
        )), is(false));
    }

    @Test
    public void whenDeleteCorrectUserShouldReturnTrue() {
        User user = new User("test", "test", "test");
        validate.init().action(Action.Type.valueOf("ADD"), user);
        assertThat(validate.init().action(Action.Type.valueOf("DELETE"), user), is(true));
    }

    @Test
    public void whenUpdateUserValuesShouldChanged() {
        User user = new User("test", "test", "test");
        user.setId(1);
        validate.init().action(Action.Type.valueOf("ADD"), user);
        User updatedUser = new User("updatedName", "updatedLogin", "updatedEmail");
        updatedUser.setId(user.getId());
        validate.init().action(Action.Type.valueOf("UPDATE"), updatedUser);
        assertThat(validate.findById(1).getName(), is("updatedName"));
        assertThat(validate.findById(1).getLogin(), is("updatedLogin"));
        assertThat(validate.findById(1).getEmail(), is("updatedEmail"));
    }

    @Test
    public void whenAddSameUserTwiceThanAddOnlyOne() {
        User user = new User("test", "test", "test");
        user.setId(1);
        User sameUser = new User("test", "test", "test");
        sameUser.setId(user.getId());
        validate.init().action(Action.Type.valueOf("ADD"), user);
        validate.init().action(Action.Type.valueOf("ADD"), user);
        validate.init().action(Action.Type.valueOf("ADD"), user);
        validate.init().action(Action.Type.valueOf("ADD"), sameUser);
        validate.init().action(Action.Type.valueOf("ADD"), sameUser);
        validate.init().action(Action.Type.valueOf("ADD"), sameUser);
        List<User> test = new CopyOnWriteArrayList<>();
        test.add(user);
        assertThat(validate.findAll(), is(test));
    }
}

class Connector {

    public Connector() {

    }

    public void createTable() {
        Properties prop = setProperties("db.properties");
        try (Connection con = DriverManager.getConnection(
                prop.getProperty("url"),
                prop.getProperty("login"),
                prop.getProperty("password"));
             Statement st = con.createStatement()
        ) {
            st.execute("CREATE TABLE IF NOT EXISTS users("
                    + "user_id SERIAL PRIMARY KEY,"
                    + "user_name VARCHAR(50) NOT NULL,"
                    + "user_login VARCHAR(50) NOT NULL,"
                    + "user_email VARCHAR(50),"
                    + "create_date TIMESTAMP);");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropTable() {
        Properties prop = setProperties("db.properties");
        try (Connection con = DriverManager.getConnection(
                prop.getProperty("url"),
                prop.getProperty("login"),
                prop.getProperty("password"));
             Statement st = con.createStatement()
        ) {
            st.execute("DROP TABLE users;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Properties setProperties(String propName) {
        InputStream is;
        Properties properties = new Properties();
        try {
            is = DbStoreTest.class.getClassLoader().getResourceAsStream(propName);
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}