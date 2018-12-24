package ru.job4j.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.model.Place;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

public class DbStore {
    private static final BasicDataSource SOURCE = new BasicDataSource();
    private final Logger logger = LogManager.getLogger(DbStore.class);
    private static final DbStore INSTANCE = new DbStore();

    private DbStore() {
        init();
    }

    /**
     * Method init get properties object, setup connections poll settings,
     * create table in DB.
     */
    private void init() {
        Properties prop = setProperties("db.properties");
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage());
        }
        SOURCE.setUrl(prop.getProperty("url"));
        SOURCE.setUsername(prop.getProperty("login"));
        SOURCE.setPassword(prop.getProperty("password"));
        SOURCE.setMinIdle(5);
        SOURCE.setMaxIdle(10);
        SOURCE.setMaxOpenPreparedStatements(100);
        logger.debug("Properties loaded.");
        createTablePlaces();
        createTableCustomers();
    }

    private void createTablePlaces() {
        try (Connection con = SOURCE.getConnection();
             Statement st = con.createStatement()
        ) {
            st.execute("CREATE TABLE IF NOT EXISTS places("
                    + "id SERIAL PRIMARY KEY,"
                    + "row INT NOT NULL, "
                    + "col INT NOT NULL "
                    + ");"
            );
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        logger.debug("Tale places created.");
    }

    private void createTableCustomers() {
        try (Connection con = SOURCE.getConnection();
             Statement st = con.createStatement()
        ) {
            st.execute("CREATE TABLE IF NOT EXISTS customers("
                    + "id SERIAL PRIMARY KEY,"
                    + "name VARCHAR(50) NOT NULL, "
                    + "phone VARCHAR(50) NOT NULL, "
                    + "place_id INT CONSTRAINT place_id_fk "
                    + "REFERENCES places(id)"
                    + ");"
            );
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        logger.debug("Tale customers created.");
    }

    /**
     * Method for get instance of DbStore.
     *
     * @return DbStore instance.
     */
    public static DbStore getStoreInstance() {
        return INSTANCE;
    }

    /**
     * Method for getting connection to database.
     *
     * @return connection.
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        return SOURCE.getConnection();
    }

    /**
     * Method for getting properties object with configuration of 'propName' file.
     *
     * @param propName properties file with configuration.
     * @return properties object with loaded configuration.
     */
    private Properties setProperties(String propName) {
        InputStream is;
        Properties properties = new Properties();
        try {
            is = DbStore.class.getClassLoader().getResourceAsStream(propName);
            properties.load(is);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return properties;
    }

    public Place getPlaceFromDefaultPlaces(Place place) {
        Place pl = null;
        try (Connection con = this.getConnection();
            PreparedStatement st = con.prepareStatement(
                    "SELECT * FROM places_default "
                    + "WHERE row = ? AND col = ?;"
            );
        ) {
            st.setInt(1, place.getRow());
            st.setInt(2, place.getCol());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Place temp = new Place(
                        rs.getInt("row"),
                        rs.getInt("col")
                );
                temp.setId(rs.getInt("id"));
                temp.setCost(rs.getDouble("cost"));
                pl = temp;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pl;
    }

}
