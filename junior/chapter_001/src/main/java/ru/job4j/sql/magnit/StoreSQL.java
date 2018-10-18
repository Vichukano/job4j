package ru.job4j.sql.magnit;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;


/**
 * Класс описывает генерацию таблицы в базе данных.
 */
public class StoreSQL {

    /**
     * Конструктор класса.
     * В конструкторе создается объект класса ConnectSQL.
     * @param path путь к properties.
     * @param name имя атрибута properties.
     * @param n количество записей в таблицу.
     * @throws SQLException
     */
    public StoreSQL(String path, String name, int n) throws SQLException {
        Properties prop = setProperties(path);
        try (ConnectSQL con = new ConnectSQL(prop, name)) {
            createTable(con);
            generate(n, con);
        }
    }

    /**
     * Метод реализует подключение к базе данных и генерирует в ней таблицу
     * entry с полем INTEGER field.
     * Применяется autocommit = false.
     * @param con объект класса ConnectSQL.
     * @throws SQLException
     */
    private void createTable(ConnectSQL con) throws SQLException {
        Statement st = null;
        try {
            con.getCon().setAutoCommit(false);
            st = con.getCon().createStatement();
            st.execute("CREATE TABLE IF NOT EXISTS 'entry' ('field' INTEGER);");
            con.getCon().commit();
        } catch (SQLException e) {
            con.getCon().rollback();
            e.printStackTrace();
        } finally {
            if (st != null) {
                st.close();
            }
        }
    }

    /**
     * Метод передает создает объект класса Properties с настойками БД.
     *
     * @param path путь к файлу настроек.
     * @return объект класса Properties.
     */
    private Properties setProperties(String path) {
        InputStream is;
        Properties properties = new Properties();
        try {
            is = StoreSQL.class.getClassLoader().getResourceAsStream(path);
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    /**
     * Метод генерирует int n записей в таблице.
     * Перед генерацией проходит проверка таблицы на пустату.
     * Если таблица не пустая, то записи в ней стираются.
     * Применяется autocommit = false.
     * Одна транзакция - n вставок значений в таблицу.
     *
     * @param n количество запией, которое будет сгенерировано.
     * @throws SQLException
     */
    private void generate(int n, ConnectSQL con) throws SQLException {
        PreparedStatement st1 = null;
        try {
            st1 = con.getCon().prepareStatement("SELECT * FROM 'entry'");
            ResultSet rs = st1.executeQuery();
            if (rs.next()) {
                PreparedStatement st2 = con.getCon().prepareStatement("DELETE FROM 'entry'");
                st2.execute();
                st2.close();
            }
            st1.close();
            for (int i = 1; i <= n; i++) {
                PreparedStatement st = con.getCon().prepareStatement("INSERT INTO 'entry'('field') VALUES (?);");
                st.setInt(1, i);
                st.executeUpdate();
                st.close();
            }
            con.getCon().commit();
        } catch (SQLException e) {
            con.getCon().rollback();
            e.printStackTrace();
        }
    }
}
