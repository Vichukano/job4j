package ru.job4j.sql.magnit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Класс описывает подкючение к базе данных.
 */
public class ConnectSQL implements AutoCloseable {
    private Connection con = null;

    /**
     * Конструктор класса.
     *
     * @param prop объект класса Properties с настройками.
     * @param name имя атрибута.
     */
    public ConnectSQL(Properties prop, String name) {
        con = getConnection(prop, name);
    }

    /**
     * Метод реализует подключение к базе данных.
     *
     * @param prop объект класса Properties с настройками.
     * @param name имя атрибута.
     * @return объект Connection.
     */
    private Connection getConnection(Properties prop, String name) {
        try {
            con = DriverManager.getConnection(prop.getProperty(name));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    /**
     * Геттер для connection.
     *
     * @return con.
     */
    public Connection getCon() {
        return this.con;
    }

    /**
     * Метод закрывает подключение к базе данных.
     *
     * @throws SQLException
     */
    @Override
    public void close() throws SQLException {
        if (con != null) {
            con.close();
        }
    }
}
