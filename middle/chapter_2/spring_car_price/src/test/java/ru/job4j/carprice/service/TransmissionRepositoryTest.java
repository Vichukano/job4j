package ru.job4j.carprice.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.job4j.carprice.configuration.Config;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Config.class})
@WebAppConfiguration
public class TransmissionRepositoryTest {

    @Autowired
    private TransmissionService service;

    @Test
    public void whenFindAllThenReturnListOfCarBodies() {
        assertThat(this.service.findAll().size(), is(3));
        assertThat(this.service.findAll().get(0).getType(), is("mechanical"));
        assertThat(this.service.findAll().get(1).getType(), is("automatic"));
        assertThat(this.service.findAll().get(2).getType(), is("robot"));
    }

    @Test
    public void whenFindByIdThenReturnCarBodyWithThisId() {
        assertThat(this.service.findById(1L).getType(), is("mechanical"));
        assertThat(this.service.findById(2L).getType(), is("automatic"));
        assertThat(this.service.findById(3L).getType(), is("robot"));
    }
}
