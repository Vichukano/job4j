package ru.job4j.carprice.persistence.implementation;

import ru.job4j.carprice.model.Image;
import ru.job4j.carprice.persistence.ImageDao;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class ImageDaoImpl extends GenericDaoImpl<Image> implements ImageDao {

    public ImageDaoImpl() {
        super(Image.class);
    }


    @Override
    public List<Image> findByName(Image image) {
        List<Image> images;
        EntityManager em = getFactory().createEntityManager();
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
