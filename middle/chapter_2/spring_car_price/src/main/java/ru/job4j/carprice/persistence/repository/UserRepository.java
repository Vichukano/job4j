package ru.job4j.carprice.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.carprice.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByLogin(String login);
}
