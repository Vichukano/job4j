package ru.job4j.carprice.persistence.implementation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.job4j.carprice.configuration.Config;
import ru.job4j.carprice.persistence.TransmissionDao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Config.class})
@WebAppConfiguration
public class TransmissionDaoImplTest {

    @Autowired
    private TransmissionDao dao;

    @Test
    public void whenFindAllThenReturnListOfTransmissions() {
        assertThat(this.dao.findAll().size(), is(3));
    }

    @Test
    public void whenFindByIdThenReturnCarTransmission() {
        assertThat(this.dao.findById(1L).getType(), is("mechanical"));
        assertThat(this.dao.findById(2L).getType(), is("automatic"));
        assertThat(this.dao.findById(3L).getType(), is("robot"));
    }
}
