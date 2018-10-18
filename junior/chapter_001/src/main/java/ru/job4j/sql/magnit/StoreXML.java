package ru.job4j.sql.magnit;

import ru.job4j.sql.magnit.models.Entry;
import ru.job4j.sql.magnit.models.Field;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Кдасс описывает запись строк таблицы из базы данных
 * в xml файл.
 */
public class StoreXML {

    /**
     * Конструктор класса.
     * @param path путь к properties.
     * @param name имя атрибута properties.
     * @throws SQLException
     */
    public StoreXML(String path, String name) throws SQLException {
        Properties prop = setProperties(path);
        try (ConnectSQL con = new ConnectSQL(prop, name)) {
            createXML(con);
        }
    }

    /**
     * Метод получает объект класса ResultSet, содержайщий значения
     * всех строк таблицы.
     * @param con объект класса ConnectSQL.
     * @return объект ResultSet.
     */
    private ResultSet getValuesFromTable(ConnectSQL con) {
        ResultSet rs = null;
        try {
            Statement st = con.getCon().createStatement();
            rs = st.executeQuery("SELECT * FROM 'entry';");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    /**
     * Метод генерирует объект Entry с полями из таблицы.
     * @param con объект класса ConnectSQL.
     * @return объект класса Entry.
     */
    private Entry createEntries(ConnectSQL con) {
        ResultSet rs = getValuesFromTable(con);
        List<Field> list = new ArrayList<>();
        try {
            while (rs.next()) {
                list.add(new Field(rs.getInt("field")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Entry(list);
    }

    /**
     * Метод генерирует файл xml.
     * @param con объект класса ConnectSQL.
     */
    private void createXML(ConnectSQL con) {
        Entry entry = createEntries(con);
        try {
            JAXBContext context = JAXBContext.newInstance(Entry.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(entry, new File("junior/chapter_001/src/main/java/ru/job4j/sql/magnit/resource/entry.xml"));
        } catch (JAXBException e) {
            e.printStackTrace();
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
}

