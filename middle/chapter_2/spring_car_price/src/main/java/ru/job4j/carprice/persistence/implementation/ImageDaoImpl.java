package ru.job4j.carprice.persistence.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.job4j.carprice.model.Image;
import ru.job4j.carprice.persistence.ImageDao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

@Component
public class ImageDaoImpl extends GenericDaoImpl<Image> implements ImageDao {

    @Autowired
    private EntityManagerFactory factory;

    public ImageDaoImpl() {
        super(Image.class);
    }


    @Override
    public List<Image> findByName(Image image) {
        List<Image> images;
        EntityManager em = this.factory.createEntityManager();
        try {
            em.getTransaction().begin();
            Query query = em.createQuery("FROM " + Image.class.getName() + " i WHERE i.url =:url");
            query.setParameter("url", image.getUrl());
            images = query.getResultList();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw (e);
        } finally {
            em.close();
        }
        return images;
    }
}
