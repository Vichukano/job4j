package ru.job4j.carprice.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.carprice.model.User;
import ru.job4j.carprice.persistence.repository.UserRepository;

import javax.persistence.EntityNotFoundException;

/**
 * Class for service methods with User objects.
 * Singleton by default.
 */
@Service
public class UserService {
    private final UserRepository repository;
    private final Logger logger = LogManager.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public void add(User user) {
        this.repository.save(user);
    }

    public User findById(long id) {
        return this.repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
    }

    public User findByLogin(String login) {
        try {
            return this.repository.findByLogin(login);
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
        User found = this.repository.findByLogin(login);
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
        User found = this.repository.findByLogin(user.getLogin());
        if (found != null) {
            result = true;
            logger.debug("User exist.");
        }
        return result;
    }
}
