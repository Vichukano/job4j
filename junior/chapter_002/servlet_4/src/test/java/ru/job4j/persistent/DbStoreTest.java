package ru.job4j.persistent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DbStoreTest {
    private final Store dbStore = DbStore.getInstance();
    private Connector connector = new Connector();

    @Before
    public void setUp() {
        this.connector.createTable();
    }

    @After
    public void reset() {
        this.connector.dropTable();
    }


    @Test
    public void whenAddUserToDbShouldReturnTrue() {
        User user = new User("test", "test", "test");
        assertThat(dbStore.add(user), is(true));
    }

    @Test
    public void whenExtractUserFromDbShouldReturnCorrectUser() {
        User user = new User("test", "test", "test");
        dbStore.add(user);
        assertThat(dbStore.findById(1).getName(), is("test"));
        assertThat(dbStore.findById(1).getLogin(), is("test"));
        assertThat(dbStore.findById(1).getEmail(), is("test"));
        assertThat(dbStore.findById(1).getCreateDate(), is(user.getCreateDate()));
    }

    @Test
    public void whenDeleteUserFromDbShouldReturnTrue() {
        User user = new User("test", "test", "test");
        dbStore.add(user);
        assertThat(dbStore.delete(1), is(true));
        assertThat(dbStore.delete(1), is(false));
        assertThat(dbStore.findAll(), is(new CopyOnWriteArrayList<>()));
    }

    @Test
    public void whenUpdateUserValuesShouldChanged() {
        User user = new User("test", "test", "test");
        dbStore.add(user);
        User updatedUser = new User("updated", "updated", "updated");
        dbStore.update(1, updatedUser);
        assertThat(dbStore.findById(1).getName(), is("updated"));
        assertThat(dbStore.findById(1).getLogin(), is("updated"));
        assertThat(dbStore.findById(1).getEmail(), is("updated"));
        assertThat(dbStore.findById(1).getCreateDate(), is(user.getCreateDate()));
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
