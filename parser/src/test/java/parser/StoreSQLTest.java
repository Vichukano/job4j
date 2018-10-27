package parser;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;
import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class StoreSQLTest {
    private Map<Date, String> messages;
    private Connection con = null;
    private final String propName = "test.properties";

    private Connection createConnection() {
        Properties prop = setProperties(propName);
        try {
            this.con = DriverManager.getConnection(
                    prop.getProperty("url")
                            + prop.getProperty("db_name"),
                    prop.getProperty("login"),
                    prop.getProperty("password")
            );
            this.con.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this.con;
    }

    private void closeConnection() {
        if (this.con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private Properties setProperties(String propName) {
        InputStream is;
        Properties properties = new Properties();
        try {
            is = StoreSQL.class.getClassLoader().getResourceAsStream(propName);
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    @Before
    public void setUp() {
        this.messages = new HashMap<>();
        messages.put(new Date(123L), "test_1");
        messages.put(new Date(124L), "test_2");
        messages.put(new Date(125L), "test_3");
        messages.put(new Date(126L), "test_4");
        messages.put(new Date(127L), "test_5");
    }


    @After
    public void deleteTestTable() throws SQLException {
        this.con = createConnection();
        Statement st = con.createStatement();
        st.executeUpdate("DROP TABLE jobs;");
        con.commit();
        closeConnection();
        System.out.println("Таблица jobs удалена.");
    }



    @Test
    public void whenAddValuesToDataBaseThenValuesShouldBeAdded() throws SQLException {
        StoreSQL sql = new StoreSQL(propName);
        sql.addValuesToTable(messages);
        this.con = createConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT description FROM jobs ORDER BY description;");
        List<String> test = new ArrayList<>();
        while (rs.next()) {
            test.add(rs.getString("description"));
        }
        closeConnection();
        assertThat(test, is(Arrays.asList("test_1", "test_2", "test_3", "test_4", "test_5")));
    }

    @Test
    public void whenInsertNotUniqueValuesShouldIgnoreIt() throws SQLException {
        messages.put(new Date(128L), "test_3");
        messages.put(new Date(129L), "test_4");
        messages.put(new Date(110L), "test_5");
        messages.put(new Date(111L), "test_6");
        StoreSQL sql = new StoreSQL(propName);
        sql.addValuesToTable(messages);
        this.con = createConnection();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("SELECT description FROM jobs ORDER BY description;");
        List<String> test = new ArrayList<>();
        while (rs.next()) {
            test.add(rs.getString("description"));
        }
        closeConnection();
        assertThat(test, is(Arrays.asList("test_1", "test_2", "test_3", "test_4", "test_5", "test_6")));
    }
}
