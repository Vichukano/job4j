package ru.job4j.carprice.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.carprice.model.User;
import ru.job4j.carprice.persistence.UserDao;

/**
 * Class for service methods with User objects.
 * Singleton by default.
 */
@Service
public class UserService {
    private final UserDao store;
    private final Logger logger = LogManager.getLogger(UserService.class);

    @Autowired
    public UserService(UserDao store) {
        this.store = store;
    }

    public void add(User user) {
        try {
            this.store.add(user);
        } catch (Exception e) {
            logger.error("Failed to add user.", e);
        }
    }

    public User findById(long id) {
        try {
            return this.store.findById(id);
        } catch (Exception e) {
            logger.error("Failed find user by id.", e);
            return null;
        }
    }

    public User findByLogin(String login) {
        User user = new User();
        user.setLogin(login);
        try {
            return this.store.findByParam(user);
        } catch (Exception e) {
            logger.debug("Failed to find user with this login.", e);
            return null;
        }
    }

    /**
     * Method check that user with this login and password store in database.
     * @param login - user login
     * @param password - user password.
     * @return true if user store in database, else false.
     */
    public boolean isCredential(String login, String password) {
        boolean result = false;
        User user = new User();
        user.setLogin(login);
        User found = this.store.findByParam(user);
        if (found != null && password.equals(found.getPassword())) {
            result = true;
            logger.debug("User exist.");
        }
        return result;
    }

    /**
     * Method for check that user store in database.
     * @param user - user object.
     * @return true if user exist, else false.
     */
    public boolean isExist(User user) {
        boolean result = false;
        User found = this.store.findByParam(user);
        if (found != null) {
            result = true;
            logger.debug("User exist.");
        }
        return result;
    }
}
