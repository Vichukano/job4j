package ru.job4j.persistent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.model.Role;
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
    private final Store<User> dbStore = DbStore.getInstance();
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
        user.setRoleId(1);
        assertThat(dbStore.add(user), is(true));
    }

    @Test
    public void whenExtractUserFromDbShouldReturnCorrectUser() {
        User user = new User("test", "test", "test");
        user.setRoleId(1);
        dbStore.add(user);
        assertThat(dbStore.findById(1).getLogin(), is("test"));
        assertThat(dbStore.findById(1).getPassword(), is("test"));
        assertThat(dbStore.findById(1).getEmail(), is("test"));
        assertThat(dbStore.findById(1).getCreateDate(), is(user.getCreateDate()));
    }

    @Test
    public void whenDeleteUserFromDbShouldReturnTrue() {
        User user = new User("test", "test", "test");
        user.setRoleId(1);
        dbStore.add(user);
        assertThat(dbStore.delete(1), is(true));
        assertThat(dbStore.delete(1), is(false));
        assertThat(dbStore.findAll(), is(new CopyOnWriteArrayList<>()));
    }

    @Test
    public void whenUpdateUserValuesShouldChanged() {
        User user = new User("test", "test", "test");
        user.setRoleId(1);
        dbStore.add(user);
        User updatedUser = new User("updated", "updated", "updated");
        updatedUser.setRoleId(1);
        dbStore.update(1, updatedUser);
        assertThat(dbStore.findById(1).getLogin(), is("updated"));
        assertThat(dbStore.findById(1).getPassword(), is("updated"));
        assertThat(dbStore.findById(1).getEmail(), is("updated"));
        assertThat(dbStore.findById(1).getCreateDate(), is(user.getCreateDate()));
        assertThat(dbStore.findById(1).getRoleId(), is(1));
    }

    @Test
    public void whenFindRoleByNameShouldFindIt() {
        Role role = DbStore.getInstance().findRoleByName("Admin");
        Role role2 = DbStore.getInstance().findRoleByName("User");
        assertThat(role.getName(), is("Admin"));
        assertThat(role.getRoleId(), is(1));
        assertThat(role2.getName(), is("User"));
        assertThat(role2.getRoleId(), is(2));
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
            st.execute("INSERT INTO roles(role_name) VALUES ('Admin')");
            st.execute("INSERT INTO roles(role_name) VALUES ('User')");
            st.execute("CREATE TABLE IF NOT EXISTS users("
                    + "user_id SERIAL PRIMARY KEY,"
                    + "user_login VARCHAR(50) NOT NULL,"
                    + "user_password VARCHAR(50) NOT NULL,"
                    + "user_email VARCHAR(50),"
                    + "create_date TIMESTAMP,"
                    + "user_role_id INT NOT NULL,"
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
