package ru.job4j.carprice.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.carprice.model.Image;
import ru.job4j.carprice.persistence.Dao;
import ru.job4j.carprice.persistence.ImageDaoImpl;

import java.util.List;

public class ImageService {
    private final Dao<Image> store = new ImageDaoImpl();
    private static final ImageService INSTANCE = new ImageService();
    private final Logger logger = LogManager.getLogger(ImageService.class);

    private ImageService() {

    }

    public static ImageService getInstance() {
        return INSTANCE;
    }

    public void add(Image image) {
        try {
            this.store.add(image);
        } catch (Exception e) {
            logger.error("Failed to add image.", e);
        }
    }

    public void delete(Image image) {
        try {
            this.store.delete(image);
        } catch (Exception e) {
            logger.error("Failed to delete image", e);
        }
    }

    public void update(Image image) {
        try {
            this.store.update(image);
        } catch (Exception e) {
            logger.error("Failed to update image.", e);
        }
    }

    public Image findCarById(Image image) {
        try {
            return this.store.findCarById(image.getId());
        } catch (Exception e) {
            logger.error("Failed to find by id.", e);
            return null;
        }
    }

    public List<Image> findAll() {
        try {
            return this.store.findAll();
        } catch (Exception e) {
            logger.error("Failed to find all images.", e);
            return null;
        }
    }

    public List<Image> findByName(Image image) {
        try {
            return this.store.findByName(image);
        } catch (Exception e) {
            logger.error("Failed to find by url.", e);
            return null;
        }
    }
}
