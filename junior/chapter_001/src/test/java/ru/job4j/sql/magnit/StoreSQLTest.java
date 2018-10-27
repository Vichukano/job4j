package ru.job4j.sql.magnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class StoreSQLTest {
    private ConnectSQL con = null;


    @Before
    public void setUp() {
        InputStream is;
        Properties properties = new Properties();
        try {
            is = StoreSQL.class.getClassLoader().getResourceAsStream("test.properties");
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        con = new ConnectSQL(properties, "url");
    }

    @After
    public void reset() throws SQLException {
        PreparedStatement st = con.getCon().prepareStatement("DELETE FROM 'entry'");
        st.executeUpdate();
        st.close();
        con.close();
    }

    @Test
    public void whenGenerateValuesItShouldWriteIntoTable() throws SQLException {
        new StoreSQL("test.properties", "url", 5);
        PreparedStatement st = con.getCon().prepareStatement("SELECT * FROM 'entry'");
        ResultSet rs = st.executeQuery();
        List<Integer> test = new ArrayList<>();
        while (rs.next()) {
            test.add(rs.getInt("field"));
        }
        assertThat(test, is(Arrays.asList(1, 2, 3, 4, 5)));
    }
}
