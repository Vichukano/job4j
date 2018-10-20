package tracker;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TrackerDbTest {
    private String dbPath;
    private String configPath;

    @Before
    public void setup() {
        dbPath = "C:\\projects\\job4j\\tracker\\src\\test\\java\\tracker\\resources\\test_db.sql";
        configPath = "C:\\projects\\job4j\\tracker\\src\\test\\java\\tracker\\resources\\config.properties";
    }

    private Properties setProperties(String path) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(new File(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    @After
    public void dropTable() {
        Properties prop = setProperties(configPath);
        try (Connection con = DriverManager.getConnection(
                prop.getProperty("url") + prop.getProperty("db_name"),
                prop.getProperty("login"),
                prop.getProperty("password"));
            PreparedStatement st = con.prepareStatement("DROP TABLE " + prop.getProperty("table_name"))
        ) {
            st.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenReplaceNameThenReturnNewName() {
        try (TrackerDb tr = new TrackerDb(configPath, dbPath)) {
            Item previous = new Item("test1", "testDescription", 123L);
            tr.add(previous);
            Item next = new Item("test2", "testDescription2", 1234L);
            next.setId(previous.getId());
            tr.replace(previous.getId(), next);
            assertThat(tr.findById(previous.getId()).getName(), is("test2"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenAddItemPositionMustGrowUp() {
        Item item1 = new Item("1", "abc", 123L);
        Item item2 = new Item("2", "abc", 124L);
        Item item3 = new Item("3", "abc", 125L);
        try (TrackerDb tr = new TrackerDb(configPath, dbPath)) {
            tr.add(item1);
            tr.add(item2);
            tr.add(item3);
            assertThat(tr.getNumberOfItems(), is(3));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenFindAllShouldReturnAllItems() {
        Item item1 = new Item("1", "abc", 123L);
        Item item2 = new Item("2", "abc", 124L);
        Item item3 = new Item("3", "abc", 125L);
        try (TrackerDb tr = new TrackerDb(configPath, dbPath)) {
            tr.add(item1);
            tr.add(item2);
            tr.add(item3);
            List<Item> test = new ArrayList<>();
            test.add(item1);
            test.add(item2);
            test.add(item3);
            assertThat(tr.findAll(), is(test));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenFindByNameShouldReturnAllItemsWithThatName() {
        Item item1 = new Item("1", "abc", 123L);
        Item item2 = new Item("2", "abc", 124L);
        Item item3 = new Item("2", "abc", 125L);
        Item item4 = new Item("3", "abc", 126L);
        Item item5 = new Item("2", "abc", 127L);
        try (TrackerDb tr = new TrackerDb(configPath, dbPath)) {
            tr.add(item1);
            tr.add(item2);
            tr.add(item3);
            tr.add(item4);
            tr.add(item5);
            List<Item> test = new ArrayList<>();
            test.add(item2);
            test.add(item3);
            test.add(item5);
            assertThat(tr.findByName("2"), is(test));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
