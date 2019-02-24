package ru.job4j.carprice.persistence.implementation;

import org.springframework.stereotype.Component;
import ru.job4j.carprice.model.Engine;
import ru.job4j.carprice.persistence.EngineDao;

@Component
public class EngineDaoImpl extends GenericDaoImpl<Engine> implements EngineDao {

    public EngineDaoImpl() {
        super(Engine.class);
    }
}
