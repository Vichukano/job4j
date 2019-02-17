package ru.job4j.storage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("jdbc")
public class JDBCStorage implements Storage {
    private final Logger logger = LogManager.getLogger(JDBCStorage.class);
    @Autowired
    private DbConfig config;

    public JDBCStorage(DbConfig config) {
        this.config = config;
        this.init();
    }

    private void init() {
        logger.info("Driver: {}", config.getProperties().getProperty("driver"));
        logger.info("URL: {}", config.getProperties().getProperty("url"));
        logger.info("Login: {}", config.getProperties().getProperty("login"));
        logger.info("password: {}", config.getProperties().getProperty("password"));
    }

    @Override
    public void add(User user) {

    }

    @Override
    public void delete(User user) {

    }

    @Override
    public User findById(int id) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    public DbConfig getConfig() {
        return config;
    }

    public void setConfig(DbConfig config) {
        this.config = config;
    }
}
