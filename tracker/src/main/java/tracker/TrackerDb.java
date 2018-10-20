package tracker;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.AutoCloseable;
import java.sql.*;
import java.util.*;

public class TrackerDb implements AutoCloseable {
    private Connection conn = null;
    private String tableName;

    public TrackerDb(String configPath, String dbPath) {
        Properties prop = setProperties(configPath);
        try {
            conn = DriverManager.getConnection(
                    prop.getProperty("url") + prop.getProperty("db_name"),
                    prop.getProperty("login"),
                    prop.getProperty("password")
            );
            tableName = prop.getProperty("table_name");
            importTable(dbPath);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод создает таблицу items в базе данных.
     *
     * @param path путь к скрипту с таблицей.
     * @throws SQLException
     */
    public void importTable(String path) throws SQLException {
        FileInputStream is = null;
        try {
            is = new FileInputStream(new File(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try (
                Scanner sc = new Scanner(is);
                Statement st = conn.createStatement()
        ) {
            sc.useDelimiter(";");
            while (sc.hasNext()) {
                String line = sc.next();
                if (line.trim().length() > 0) {
                    st.execute(line);
                }
            }
        }
    }

    /**
     * Метод загружает конфиг в properties.
     *
     * @param path путь к файлу конфига.
     * @return properties.
     */
    private Properties setProperties(String path) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(new File(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }


    /**
     * Метод добавляет Item в таблицу items.
     * При добавлении заявки, ей присваивется уникальный id.
     *
     * @param item добавляемая заявка.
     * @return добавленная заявка.
     */
    public Item add(Item item) {
        try (PreparedStatement st = conn.prepareStatement("INSERT INTO" + " " + tableName + "(item_name, item_desc, created) VALUES (?, ?, ?)")) {
            st.setString(1, item.getName());
            st.setString(2, item.getDesc());
            st.setTimestamp(3, new Timestamp(item.getCreated()));
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (Statement statement = conn.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT item_id FROM " + tableName);
            while (rs.next()) {
                item.setId(String.valueOf(rs.getInt("item_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }


    /**
     * Метод удаляет item с передаваемым id из таблицы.
     *
     * @param id заявки, которую нужно удалить.
     */
    public boolean delete(String id) {
        boolean result = false;
        try (PreparedStatement st = conn.prepareStatement("DELETE FROM" + " " + tableName + " WHERE item_id = ?")) {
            System.out.println("Подключение к базе данных прошло успещно");
            st.setString(1, id);
            int deleted = st.executeUpdate();
            if (deleted > 0) {
                result = true;
                System.out.println("Запрос выполнен.");
            } else {
                System.out.println("Запрос не выполнен.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Метод заменяет item в таблице items с передаваемым id на передаваемый item.
     *
     * @param id   id заявки, которую нужно заменить.
     * @param item заявка, на которую будет произведена замена.
     */
    public boolean replace(String id, Item item) {
        boolean result = false;
        try (PreparedStatement st = conn.prepareStatement("UPDATE" + " " + tableName + " " + "SET item_name = ?, item_desc = ?, created = ? WHERE item_id = ?")) {
            st.setString(1, item.getName());
            st.setString(2, item.getDesc());
            st.setTimestamp(3, new Timestamp(item.getCreated()));
            st.setInt(4, Integer.parseInt(id));
            int updated = st.executeUpdate();
            if (updated > 0) {
                result = true;
                //item.setId(String.valueOf(id));
                System.out.println("Запрос выполнен.");
            } else {
                System.out.println("Запрос не выполнен.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Метод возвращает массив всех item, находящихся в таблице items.
     *
     * @return массив объектов item.
     */
    public List<Item> findAll() {
        List<Item> tmp = new ArrayList<>();
        try (PreparedStatement st = conn.prepareStatement("SELECT * FROM" + " " + tableName);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                Item newItem = new Item(
                        rs.getString("item_name"),
                        rs.getString("item_desc"),
                        Timestamp.valueOf(rs.getString("created")).getTime()
                );
                newItem.setId(String.valueOf(rs.getInt("item_id")));
                tmp.add(newItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (!tmp.isEmpty()) {
            System.out.println("Запрос выполенен успешно.");
        }
        return tmp;
    }

    /**
     * Метод находит все item с переданным name.
     *
     * @param key поле name у объекта item.
     * @return массив объектов item c переданным полем name. Если таких имен нет, то пустой массив.
     */
    public List<Item> findByName(String key) {
        List<Item> tmp = new ArrayList<>();
        try (PreparedStatement st = conn.prepareStatement("SELECT * FROM" + " " + tableName + " WHERE item_name = ?")) {
            st.setString(1, key);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Item newItem = new Item(
                            rs.getString("item_name"),
                            rs.getString("item_desc"),
                            Timestamp.valueOf(rs.getString("created")).getTime()
                    );
                    newItem.setId(String.valueOf(rs.getInt("item_id")));
                    tmp.add(newItem);
                }
            }
            if (!tmp.isEmpty()) {
                System.out.println("Запрос выполенен успешно.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tmp;
    }

    /**
     * Метод находит объект item в таблице с передаваемым id и возврашает его.
     *
     * @param id id заявки.
     * @return item c заданным id. Если такой item нет, то вернет null.
     */
    public Item findById(String id) {
        Item newItem = null;
        try (PreparedStatement st = conn.prepareStatement("SELECT * FROM" + " " + tableName + " WHERE item_id = ?")) {
            st.setInt(1, Integer.parseInt(id));
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    newItem = new Item(
                            rs.getString("item_name"),
                            rs.getString("item_desc"),
                            Timestamp.valueOf(rs.getString("created")).getTime()
                    );
                    newItem.setId(String.valueOf(id));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (newItem != null) {
            System.out.println("Запрос выполенен успешно.");
        }
        return newItem;
    }

    /**
     * Метод возвращает количество объектов item, содержащихся в базе данных.
     *
     * @return количество заявок.
     */
    public int getNumberOfItems() {
        int count = 0;
        try (PreparedStatement st = conn.prepareStatement("SELECT count(item_id) AS count FROM" + " " + tableName)) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Метод добавляет комментарий к заявке с указанным Id.
     *
     * @param id      id заявки.
     * @param comment комментарий к заявке.
     * @return true в случае добавления комментария, иначе false.
     */
    public boolean addComment(String id, String comment) {
        boolean result = false;
        Item item = findById(id);
        if (item != null) {
            item.addComment(comment);
            result = true;
        }
        return result;
    }

    /**
     * Метод генерирует уникальный Id для добавляемого Item.
     * Генерация происходит на основе перемножения текущего времени на
     * рандомное число.
     *
     * @return сгенерированное id.
     */
    private String generateId() {
        long time = System.currentTimeMillis();
        Random rand = new Random();
        return String.valueOf(time * (rand.nextInt() + 1));
    }

    @Override
    public void close() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }
}



