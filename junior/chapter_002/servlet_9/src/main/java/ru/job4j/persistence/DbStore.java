package ru.job4j.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * Util class for connecting to database.
 */
public class DbStore {
    private static final BasicDataSource SOURCE = new BasicDataSource();
    private final static Logger LOG = LogManager.getLogger(DbStore.class);
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
            LOG.error("Failed to load driver.", e);
        }
        SOURCE.setUrl(prop.getProperty("url"));
        SOURCE.setUsername(prop.getProperty("login"));
        SOURCE.setPassword(prop.getProperty("password"));
        SOURCE.setMinIdle(5);
        SOURCE.setMaxIdle(10);
        SOURCE.setMaxOpenPreparedStatements(100);
        LOG.debug("Properties loaded.");
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
            LOG.error("Failed to load properties.", e);
        }
        return properties;
    }
}
