package ru.job4j.carprice.persistence.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.job4j.carprice.model.User;
import ru.job4j.carprice.persistence.UserDao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

@Component
public class UserDaoImpl extends GenericDaoImpl<User> implements UserDao {

    @Autowired
    private EntityManagerFactory factory;

    public UserDaoImpl() {
        super(User.class);
    }

    @Override
    public User findByParam(User user) {
        User found;
        EntityManager em = this.factory.createEntityManager();
        try {
            em.getTransaction().begin();
            Query query = em.createQuery("FROM User WHERE login =:login");
            query.setParameter("login", user.getLogin());
            found = (User) query.getSingleResult();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            return null;
        } finally {
            em.close();
        }
        return found;
    }
}
