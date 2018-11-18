package ru.job4j.persistent;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Class for storage users in DB.
 * Used postgresql as DB.
 */
public class DbStore implements Store {
    private static final BasicDataSource SOURCE = new BasicDataSource();
    private static final DbStore INSTANCE = new DbStore();
    private final Logger logger = LogManager.getLogger(DbStore.class);

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
        createTable();
    }

    /**
     * Method for get instance of DbStore.
     * @return DbStore instance.
     */
    public static DbStore getInstance() {
        return INSTANCE;
    }

    /**
     * Method for getting properties object with configuration of 'propName' file.
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

    /**
     * Method for creating table users in DB.
     */
    private void createTable() {
        try (Connection con = SOURCE.getConnection();
             Statement st = con.createStatement()
        ) {
            st.execute("CREATE TABLE IF NOT EXISTS users("
                    + "user_id SERIAL PRIMARY KEY,"
                    + "user_name VARCHAR(50) NOT NULL,"
                    + "user_login VARCHAR(50) NOT NULL,"
                    + "user_email VARCHAR(50),"
                    + "create_date TIMESTAMP);"
            );
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        logger.debug("Table created.");
    }

    /**
     * Method for adding user to DB.
     * @param user model user.
     * @return true if added, else false.
     */
    @Override
    public boolean add(User user) {
        boolean result = false;
        try (Connection con = SOURCE.getConnection();
             PreparedStatement st = con.prepareStatement("INSERT INTO users("
                     + "user_name,"
                     + "user_login,"
                     + "user_email,"
                     + "create_date)"
                     + " VALUES(?, ?, ?, ?);"
             )
        ) {
            st.setString(1, user.getName());
            st.setString(2, user.getLogin());
            st.setString(3, user.getEmail());
            st.setTimestamp(4, user.getCreateDate());
            if (st.executeUpdate() > 0) {
                result = true;
                logger.debug("User {} added", user);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    /**
     * Method for updating users in DB.
     * @param id id of user in DB.
     * @param user user with changed fields.
     * @return updated user.
     */
    @Override
    public User update(int id, User user) {
        try (Connection con = SOURCE.getConnection();
             PreparedStatement st = con.prepareStatement(
                     "UPDATE users "
                             + "SET "
                             + "user_name = ?,"
                             + "user_login = ?,"
                             + "user_email = ?"
                             + "WHERE user_id = ?;"
             )
        ) {
            st.setString(1, user.getName());
            st.setString(2, user.getLogin());
            st.setString(3, user.getEmail());
            st.setInt(4, id);
            st.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        user.setId(id);
        logger.debug("User {} updated", user);
        return user;
    }

    /**
     * Method for deleting user from DB.
     * @param id id of user in DB, that must be deleted.
     * @return true if deleted, else false.
     */
    @Override
    public boolean delete(int id) {
        boolean result = false;
        try (Connection con = SOURCE.getConnection();
             PreparedStatement st = con.prepareStatement(
                     "DELETE FROM users "
                             + "WHERE user_id = ?;"
             )
        ) {
            st.setInt(1, id);
            if (st.executeUpdate() > 0) {
                result = true;
                logger.debug("User with id {} deleted", id);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    /**
     * Method return List of all users in DB.
     * @return List of users.
     */
    @Override
    public List<User> findAll() {
        List<User> tmp = new CopyOnWriteArrayList<>();
        try (Connection con = SOURCE.getConnection();
             PreparedStatement st = con.prepareStatement(
                     "SELECT * FROM users;"
             );
             ResultSet rs = st.executeQuery()
        ) {
            while (rs.next()) {
                User user = new User(
                        rs.getString("user_name"),
                        rs.getString("user_login"),
                        rs.getString("user_email")
                );
                user.setId(rs.getInt("user_id"));
                user.setCreateDate(rs.getTimestamp("create_date"));
                tmp.add(user);
            }

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return tmp;
    }

    /**
     * Method for finding user by id.
     * @param id id of user that should be find.
     * @return user with param id.
     */
    @Override
    public User findById(int id) {
        User user = null;
        try (Connection con = SOURCE.getConnection();
             PreparedStatement st = con.prepareStatement(
                     "SELECT * FROM users "
                             + "WHERE user_id =?;"
             )
        ) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                User tmpUser = new User(
                        rs.getString("user_name"),
                        rs.getString("user_login"),
                        rs.getString("user_email")
                );
                tmpUser.setCreateDate(rs.getTimestamp("create_date"));
                tmpUser.setId(id);
                user = tmpUser;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return user;
    }

    /**
     * Don't need this method in current implementation.
     * @return null.
     */
    @Override
    public List<User> getUsers() {
        return null;
    }

}
