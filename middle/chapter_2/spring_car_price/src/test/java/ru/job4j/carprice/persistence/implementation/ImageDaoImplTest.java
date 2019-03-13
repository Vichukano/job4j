package ru.job4j.carprice.persistence.implementation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.job4j.carprice.configuration.Config;
import ru.job4j.carprice.persistence.ImageDao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Config.class})
@WebAppConfiguration
public class ImageDaoImplTest {

    @Autowired
    private ImageDao dao;

    @Test
    public void whenFindAllThenReturnListOfImages() {
        assertThat(this.dao.findAll().size(), is(1));
    }

    @Test
    public void whenFindByIdThenReturnImage() {
        assertThat(this.dao.findById(1L).getUrl(), is("empty"));
    }
}
