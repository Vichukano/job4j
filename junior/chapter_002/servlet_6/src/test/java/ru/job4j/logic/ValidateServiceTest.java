package ru.job4j.logic;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.model.Role;
import ru.job4j.model.User;
import ru.job4j.persistent.DbStore;
import ru.job4j.persistent.DbStoreTest;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Properties;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ValidateServiceTest {
    private final Validate validate = ValidateService.getInstance();
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
        user.setRoleId(2);
        User sameUser = new User("test", "test", "test");
        sameUser.setId(user.getId());
        sameUser.setRoleId(2);
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
        user.setId(1);
        user.setRoleId(2);
        validate.init().action(Action.Type.valueOf("ADD"), user);
        assertThat(validate.init().action(Action.Type.valueOf("DELETE"), user), is(true));
        assertThat(validate.findAll().size(), is(0));
    }

    @Test
    public void whenUpdateUserValuesShouldChanged() {
        User user = new User("test", "test", "test");
        user.setId(1);
        user.setRoleId(1);
        validate.init().action(Action.Type.valueOf("ADD"), user);
        User updatedUser = new User("updatedName", "updatedLogin", "updatedEmail");
        updatedUser.setId(user.getId());
        updatedUser.setRoleId(1);
        validate.init().action(Action.Type.valueOf("UPDATE"), updatedUser);
        assertThat(validate.findById(1).getLogin(), is("updatedName"));
        assertThat(validate.findById(1).getPassword(), is("updatedLogin"));
        assertThat(validate.findById(1).getEmail(), is("updatedEmail"));
    }

    @Test
    public void whenAddSameUserTwiceThanAddOnlyOne() {
        User user = new User("test", "test", "test");
        user.setId(4);
        user.setRoleId(1);
        User sameUser = new User("test", "test", "test");
        sameUser.setId(user.getId());
        sameUser.setRoleId(1);
        validate.init().action(Action.Type.valueOf("ADD"), user);
        validate.init().action(Action.Type.valueOf("ADD"), user);
        validate.init().action(Action.Type.valueOf("ADD"), user);
        validate.init().action(Action.Type.valueOf("ADD"), sameUser);
        validate.init().action(Action.Type.valueOf("ADD"), sameUser);
        validate.init().action(Action.Type.valueOf("ADD"), sameUser);
        assertThat(validate.findAll(), is(Arrays.asList(user)));
    }

    @Test
    public void whenFindRoleByNameShouldReturnCorrectRole() {
        Role role = DbStore.getInstance().findRoleByName("Admin");
        Role role2 = DbStore.getInstance().findRoleByName("User");
        assertThat(role.getName(), is("Admin"));
        assertThat(role2.getName(), is("User"));
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
            st.execute("CREATE TABLE IF NOT EXISTS roles("
                    + "role_id SERIAL PRIMARY KEY,"
                    + "role_name VARCHAR(50) NOT NULL"
                    + ");"
            );
            st.execute("INSERT INTO roles(role_name) "
                    + "VALUES ('Admin'), ('User') ON CONFLICT DO NOTHING;"

            );
            st.execute("CREATE TABLE IF NOT EXISTS users("
                    + "user_id SERIAL PRIMARY KEY,"
                    + "user_login VARCHAR(50) NOT NULL,"
                    + "user_password VARCHAR(50),"
                    + "user_email VARCHAR(50),"
                    + "create_date TIMESTAMP,"
                    + "user_role_id INT,"
                    + "CONSTRAINT roles_role_id_fk "
                    + "FOREIGN KEY (user_role_id)"
                    + "REFERENCES roles(role_id)"
                    + ");"
            );
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
            st.execute("DROP TABLE roles CASCADE;");
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