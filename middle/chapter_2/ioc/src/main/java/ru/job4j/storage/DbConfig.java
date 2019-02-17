package ru.job4j.storage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DbConfig {
    private String fileName;
    private Properties properties = new Properties();

    public DbConfig() {

    }

    public DbConfig(String fileName) {
        this.fileName = fileName;
        this.buildProperties(fileName);
    }

    private void buildProperties(String fileName) {
        InputStream is;
        try {
            is = DbConfig.class.getClassLoader().getResourceAsStream(fileName);
            this.properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Properties getProperties() {
        return properties;
    }

}
