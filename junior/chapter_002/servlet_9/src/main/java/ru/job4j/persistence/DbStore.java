package ru.job4j.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

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
            st.execute("CREATE TABLE IF NOT EXISTS places_default("
                    + "id SERIAL PRIMARY KEY,"
                    + "row INT NOT NULL, "
                    + "col INT NOT NULL, "
                    + "cost NUMERIC(5, 2), "
                    + "reserved BOOLEAN DEFAULT FALSE"
                    + ");"
            );
            st.execute("INSERT INTO places_default(row, col, cost) "
                    + "VALUES (1 , 1, 900.00), (1 , 2, 900.00), (1 , 3, 900.00), (1 , 4, 900.00), (1 , 5, 900.00), "
                    + "(2 , 1, 800.00), (2 , 2, 800.00), (2 , 3, 800.00), (2 , 4, 800.00), (2 , 5, 800.00), "
                    + "(3 , 1, 600.00), (3 , 2, 600.00), (3 , 3, 600.00), (3 , 4, 600.00), (3 , 5, 600.00), "
                    + "(4 , 1, 400.00), (4 , 2, 400.00), (4 , 3, 400.00), (4 , 4, 400.00), (4 , 5, 400.00), "
                    + "(5 , 1, 200.00), (5 , 2, 200.00), (5 , 3, 200.00), (5 , 4, 200.00), (5 , 5, 200.00);"
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
                    + "REFERENCES places_default(id),"
                    + "row INT, "
                    + "cal INT"
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
}
