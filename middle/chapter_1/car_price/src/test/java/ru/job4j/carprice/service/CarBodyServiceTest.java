package ru.job4j.carprice.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.carprice.model.CarBody;
import ru.job4j.carprice.persistence.CarBodyDaoImpl;
import ru.job4j.carprice.util.EntityManagerFactoryUtil;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CarBodyServiceTest {
    private final CarBodyService service = CarBodyService.getInstance();
    private final CarBodyDaoImpl dao = new CarBodyDaoImpl();

    @Before
    public void setUp() {
        CarBody sedan = new CarBody("sedan");
        CarBody hatch = new CarBody("hatchback");
        this.dao.add(sedan);
        this.dao.add(hatch);
    }

    @After
    public void reset() {
        EntityManager em = EntityManagerFactoryUtil
                .getInstance()
                .getEntityManagerFactory()
                .createEntityManager();
        em.getTransaction().begin();
        Query bodyQuery = em.createQuery("delete from CarBody ");
        bodyQuery.executeUpdate();
        em.getTransaction().commit();
    }

    @Test
    public void whenFindByIdThenReturnBody() {
        CarBody found = this.service.findById(1L);
        assertThat(found.getType(), is("sedan"));
    }

    @Test
    public void whenFindAllThenReturnBodyList() {
        List<CarBody> bodies = this.service.findAll();
        assertThat(bodies.size(), is(2));
        assertThat(bodies.get(0).getType(), is("sedan"));
        assertThat(bodies.get(1).getType(), is("hatchback"));
    }

}
