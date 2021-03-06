package ru.job4j.carprice.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.carprice.model.Image;
import ru.job4j.carprice.persistence.ImageDao;

import java.util.List;

/**
 * Class for service methods with Image objects.
 * Singleton by default.
 */
@Service
public class ImageService {
    private final ImageDao store;
    private final Logger logger = LogManager.getLogger(ImageService.class);

    @Autowired
    private ImageService(ImageDao store) {
        this.store = store;
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
            this.store.delete(image.getId());
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
            return this.store.findById(image.getId());
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
