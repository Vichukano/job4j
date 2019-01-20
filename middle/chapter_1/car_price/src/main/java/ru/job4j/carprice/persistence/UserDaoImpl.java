package ru.job4j.carprice.persistence;

import ru.job4j.carprice.model.User;
import ru.job4j.carprice.util.EntityManagerFactoryUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

public class UserDaoImpl implements Dao<User> {
    private final EntityManagerFactory factory = EntityManagerFactoryUtil.getInstance().getEntityManagerFactory();


    @Override
    public void add(User user) {
        EntityManager em = this.factory.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw (e);
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(User model) {

    }

    @Override
    public void update(User model) {

    }

    @Override
    public User findById(long id) {
        User found;
        EntityManager em = this.factory.createEntityManager();
        try {
            em.getTransaction().begin();
            found = em.find(User.class, id);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw (e);
        } finally {
            em.close();
        }
        return found;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public List<User> findByName(User user) {
        return null;
    }

    @Override
    public User findByParam(User user) {
        User found;
        EntityManager em = this.factory.createEntityManager();
        try {
            em.getTransaction().begin();
            Query query = em.createQuery("from User where login =:login");
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
