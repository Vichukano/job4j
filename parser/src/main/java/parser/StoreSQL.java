package parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

/**
 * Класс описывает подключение к базе данных и выполнение запросов.
 */
public class StoreSQL {
    private Connection con = null;
    private static final Logger LOG = LogManager.getLogger(StoreSQL.class);
    private String config;

    /**
     * При создании экземпляра класса, создается таблицв в базе данных.
     *
     * @param config имя файла с конфигурациями.
     */
    public StoreSQL(String config) {
        this.config = config;
        createTable();
    }

    /**
     * Метод открывает подключение к базе данных.
     *
     * @return con - открытое подключение к базе данных.
     */
    private Connection createConnection() {
        Properties prop = setProperties(config);
        try {
            this.con = DriverManager.getConnection(
                    prop.getProperty("url")
                            + prop.getProperty("db_name"),
                    prop.getProperty("login"),
                    prop.getProperty("password")
            );
            this.con.setAutoCommit(false);
            LOG.debug("Открыто подключение к БД.");
        } catch (SQLException e) {
            LOG.error("Error SQLException", e);
            try {
                this.con.rollback();
            } catch (SQLException e1) {
                LOG.error("Error SQLException", e);
            }
        }
        return this.con;
    }

    /**
     * Метод закрывает подключение к базе данных.
     */
    private void closeConnection() {
        if (this.con != null) {
            try {
                con.close();
                LOG.debug("Подключение к БД закрыто.");
            } catch (SQLException e) {
                LOG.error("Error SQLException", e);
            }
        }
    }

    /**
     * Метод реализует создание таблицы "jobs" в базе данных.
     * Таблица создается, если не существует.
     * В таблице создается уникальный индекс "description" - где будет храниться текст вакансии.
     * Таким образом в таблицу не будут добавляться дубликаты вакансий.
     */
    private void createTable() {
        try {
            con = createConnection();
            Statement st = con.createStatement();
            st.execute(
                    "CREATE TABLE IF NOT EXISTS jobs("
                            + "job_id SERIAL PRIMARY KEY, "
                            + "created_date TIMESTAMP NOT NULL, "
                            + "description VARCHAR NOT NULL);"
            );
            st.close();
            st = con.createStatement();
            st.execute("CREATE UNIQUE INDEX ON jobs(md5(description));");
            con.commit();
            LOG.debug("Создана таблица jobs.");
        } catch (SQLException e) {
            LOG.error("Error SQLException", e);
        } finally {
            closeConnection();
        }
    }

    /**
     * Метод принимает имя файла с настройками и на основе него создает объект properties.
     *
     * @param propName имя файла с настройками.
     * @return объект класса properties с загруженными настройками.
     */
    private Properties setProperties(String propName) {
        InputStream is;
        Properties properties = new Properties();
        try {
            is = StoreSQL.class.getClassLoader().getResourceAsStream(propName);
            LOG.debug("Настройки получены.");
            properties.load(is);
        } catch (IOException e) {
            LOG.error("Error IOException", e);
        }
        return properties;
    }

    /**
     * Метод добавляет данные из словаря в таблицу базы данных.
     * В запросе используется "ON CONFLICT DO NOTHING" для того, чтобы
     * в таблицу не добавлялись дубликаты записей.
     *
     * @param messages словарь вакансий.
     */
    public void addValuesToTable(Map<Date, String> messages) {
        try {
            this.con = createConnection();
            for (Map.Entry entry : messages.entrySet()) {
                try (PreparedStatement st = this.con.prepareStatement(
                        "INSERT INTO jobs(created_date, description) VALUES (?, ?) ON CONFLICT DO NOTHING;"
                )) {
                    Date date = (Date) entry.getKey();
                    st.setTimestamp(1, new Timestamp(date.getTime()));
                    st.setString(2, String.valueOf(entry.getValue()));
                    st.executeUpdate();
                }
            }
            this.con.commit();
            LOG.debug("Коммит.");
        } catch (SQLException e) {
            LOG.error("Error SQLException", e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                LOG.error("Error SQLException", e);
            }
        } finally {
            closeConnection();
        }
    }
}
