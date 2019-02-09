package ru.job4j.carprice.persistence.implementation;

import ru.job4j.carprice.model.Engine;
import ru.job4j.carprice.persistence.EngineDao;

public class EngineDaoImpl extends GenericDaoImpl<Engine> implements EngineDao {

    public EngineDaoImpl() {
        super(Engine.class);
    }
}
