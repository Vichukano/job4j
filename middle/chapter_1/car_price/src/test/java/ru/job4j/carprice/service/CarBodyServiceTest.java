package ru.job4j.carprice.service;

import org.junit.Ignore;
import org.junit.Test;
import ru.job4j.carprice.model.CarBody;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CarBodyServiceTest {
    private final CarBodyService service = CarBodyService.getInstance();

    @Ignore
    @Test
    public void whenFindByIdThenReturnBody() {
        CarBody found = this.service.findById(1L);
        assertThat(found.getType(), is("sedan"));
    }

    @Ignore
    @Test
    public void whenFindAllThenReturnBodyList() {
        List<CarBody> bodies = this.service.findAll();
        assertThat(bodies.size(), is(3));
        assertThat(bodies.get(0).getType(), is("sedan"));
        assertThat(bodies.get(1).getType(), is("hatchback"));
        assertThat(bodies.get(2).getType(), is("crossover"));
    }
}
