package ru.job4j.carprice.persistence;

import ru.job4j.carprice.model.Image;

import java.util.List;

public interface ImageDao extends GenericDao<Image> {

    List<Image> findByName(Image image);

}
