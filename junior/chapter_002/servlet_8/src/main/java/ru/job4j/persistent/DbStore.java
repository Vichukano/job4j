package ru.job4j.persistent;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.model.Role;
import ru.job4j.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
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
        createRoleTable();
        createUserTable();
    }

    /**
     * Method for get instance of DbStore.
     *
     * @return DbStore instance.
     */
    public static Store getInstance() {
        return INSTANCE;
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

    /**
     * Method for creating table roles in DB.
     * When create table, then add two roles.
     * Admin - role with id = 1,
     * User - role with id = 2.
     */
    private void createRoleTable() {
        try (Connection con = SOURCE.getConnection();
             Statement st = con.createStatement()
        ) {
            st.execute("CREATE TABLE IF NOT EXISTS roles("
                    + "role_id SERIAL PRIMARY KEY,"
                    + "role_name VARCHAR(50) NOT NULL"
                    + ");"
            );
            st.execute("INSERT INTO roles(role_id, role_name) "
                    + "VALUES (1, 'Admin'), "
                    + "(2, 'User') "
                    + "ON CONFLICT (role_id) DO NOTHING;"
            );
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        logger.debug("Table roles created.");
    }

    /**
     * Method for creating table users in DB.
     * When called, create user - admin in table users, if not exist.
     */
    private void createUserTable() {
        try (Connection con = SOURCE.getConnection();
             Statement st = con.createStatement()
        ) {
            st.execute("CREATE TABLE IF NOT EXISTS users("
                    + "user_id SERIAL PRIMARY KEY,"
                    + "user_login VARCHAR(50) NOT NULL,"
                    + "user_password VARCHAR(50) NOT NULL,"
                    + "user_email VARCHAR(50),"
                    + "create_date TIMESTAMP,"
                    + "country VARCHAR(50),"
                    + "city VARCHAR(50),"
                    + "user_role_id INT,"
                    + "CONSTRAINT roles_role_id_fk "
                    + "FOREIGN KEY (user_role_id)"
                    + "REFERENCES roles(role_id)"
                    + ");"
            );
            st.execute("INSERT INTO users(user_id, user_login, user_password, user_email, user_role_id) "
                    + "VALUES (1, 'root', 'root', 'root@root.com', 1) ON CONFLICT (user_id) DO NOTHING;"
            );
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        logger.debug("Table users created.");
    }

    /**
     * Method for adding user to DB.
     *
     * @param user model user.
     * @return true if added, else false.
     */
    @Override
    public boolean add(User user) {
        boolean result = false;
        try (Connection con = SOURCE.getConnection();
             PreparedStatement st = con.prepareStatement("INSERT INTO users("
                     + "user_login,"
                     + "user_password,"
                     + "user_email,"
                     + "create_date,"
                     + "country,"
                     + "city,"
                     + "user_role_id)"
                     + " VALUES(?, ?, ?, ?, ?, ?, ?);"
             )
        ) {
            st.setString(1, user.getLogin());
            st.setString(2, user.getPassword());
            st.setString(3, user.getEmail());
            st.setTimestamp(4, user.getCreateDate());
            st.setString(5, user.getCountry());
            st.setString(6, user.getCity());
            st.setInt(7, user.getRoleId());
            if (st.executeUpdate() > 0) {
                result = true;
                logger.debug("User {} added", user);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    @Override
    public boolean add(Role role) {
        return false;
    }

    /**
     * Method for updating users in DB.
     *
     * @param id   id of user in DB.
     * @param user user with changed fields.
     * @return updated user.
     */
    @Override
    public User update(int id, User user) {
        try (Connection con = SOURCE.getConnection();
             PreparedStatement st = con.prepareStatement(
                     "UPDATE users "
                             + "SET "
                             + "user_login = ?,"
                             + "user_password = ?,"
                             + "user_email = ?,"
                             + "country = ?,"
                             + "city = ?,"
                             + "user_role_id = ?"
                             + "WHERE user_id = ?;"
             )
        ) {
            st.setString(1, user.getLogin());
            st.setString(2, user.getPassword());
            st.setString(3, user.getEmail());
            st.setString(4, user.getCountry());
            st.setString(5, user.getCity());
            st.setInt(6, user.getRoleId());
            st.setInt(7, id);
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
     *
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

    @Override
    public Role findRoleByName(String name) {
        Role role = null;
        try (Connection con = SOURCE.getConnection();
             PreparedStatement st = con.prepareStatement(
                     "SELECT * FROM roles "
                     + "WHERE role_name = ?;"
             )
        ) {
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Role tmpRole = new Role(
                        rs.getString("role_name")
                );
                tmpRole.setRoleId(rs.getInt("role_id"));
                role = tmpRole;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return role;
    }

    /**
     * Method return List of all users in DB.
     *
     * @return List of users.
     */
    @Override
    public List<User> findAll() {
        List<User> tmp = new CopyOnWriteArrayList<>();
        try (Connection con = SOURCE.getConnection();
             PreparedStatement st = con.prepareStatement(
                     "SELECT * FROM users "
                             + "LEFT OUTER JOIN roles "
                             + "ON users.user_role_id = roles.role_id;"
             );
             ResultSet rs = st.executeQuery()
        ) {
            while (rs.next()) {
                User user = new User(
                        rs.getString("user_login"),
                        rs.getString("user_password"),
                        rs.getString("user_email")
                );
                user.setId(rs.getInt("user_id"));
                user.setCreateDate(rs.getTimestamp("create_date"));
                user.setRoleId(rs.getInt("user_role_id"));
                user.setRoleName(rs.getString("role_name"));
                user.setCountry(rs.getString("country"));
                user.setCity(rs.getString("city"));
                tmp.add(user);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return tmp;
    }

    /**
     * Method for finding user by id.
     *
     * @param id id of user that should be find.
     * @return user with param id.
     */
    @Override
    public User findById(int id) {
        User user = null;
        try (Connection con = SOURCE.getConnection();
             PreparedStatement st = con.prepareStatement(
                     "SELECT * FROM users "
                             + "LEFT OUTER JOIN roles "
                             + "ON users.user_role_id = roles.role_id "
                             + "WHERE user_id =?;"
             )
        ) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                User tmpUser = new User(
                        rs.getString("user_login"),
                        rs.getString("user_password"),
                        rs.getString("user_email")
                );
                tmpUser.setCreateDate(rs.getTimestamp("create_date"));
                tmpUser.setRoleId(rs.getInt("user_role_id"));
                tmpUser.setRoleName(rs.getString("role_name"));
                tmpUser.setCountry(rs.getString("country"));
                tmpUser.setCity(rs.getString("city"));
                tmpUser.setId(id);
                user = tmpUser;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return user;
    }

    /**
     * Method for finding user by login.
     * @param login user login.
     * @return user with login.
     */
    @Override
    public User findByLogin(String login) {
        User user = null;
        try (Connection con = SOURCE.getConnection();
             PreparedStatement st = con.prepareStatement(
                     "SELECT * FROM users "
                             + "LEFT OUTER JOIN roles "
                             + "ON users.user_role_id = roles.role_id "
                             + "WHERE user_login =?;"
             )
        ) {
            st.setString(1, login);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                User tmpUser = new User(
                        rs.getString("user_login"),
                        rs.getString("user_password"),
                        rs.getString("user_email")
                );
                tmpUser.setCreateDate(rs.getTimestamp("create_date"));
                tmpUser.setRoleId(rs.getInt("user_role_id"));
                tmpUser.setRoleName(rs.getString("role_name"));
                tmpUser.setId(rs.getInt("user_id"));
                tmpUser.setCountry(rs.getString("country"));
                tmpUser.setCity(rs.getString("city"));
                user = tmpUser;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return user;
    }

    /**
     * Don't need this method in current implementation.
     *
     * @return null.
     */
    @Override
    public List<Role> getRoles() {
        return null;
    }

    @Override
    public int getCountryIdByName(String name) {
        int id = 0;
        try (Connection con = SOURCE.getConnection();
             PreparedStatement st = con.prepareStatement(
                     "SELECT id FROM countries "
                     + "WHERE country_name = ?;"
             )
        ) {
            st.setString(1,name);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public List<String> getCitiesByCountryId(int id) {
        List<String> cities = new ArrayList<>();
        try (Connection con = SOURCE.getConnection();
            PreparedStatement st = con.prepareStatement(
                    "SELECT city_name FROM cities "
                    + "WHERE country_id = ?;"
            )
        ) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                cities.add(rs.getString("city_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cities;
    }
}
