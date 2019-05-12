package ru.job4j.io.search;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SearchTest {
    private final FilesGenerator generator = new FilesGenerator();
    private final File testDirectory = new File(System.getProperty("java.io.tmpdir"));


    @Before
    public void generateTestFiles() throws IOException {
        this.generator.generate();
    }

    @After
    public void deleteTestFiles() {
        this.generator.delete();
    }

    @Test
    public void whenSearchJpgFilesThenFindIt() {
        Search search = new Search();
        String root = this.testDirectory.getPath() + "/root";
        assertThat(search.files(root, Arrays.asList("jpg")).size(), is(1110));
    }

    @Test
    public void whenSearchTxtFilesThenFindIt() {
        Search search = new Search();
        String root = this.testDirectory.getPath() + "/root";
        assertThat(search.files(root, Arrays.asList("txt")).size(), is(1110));
    }

    @Test
    public void whenSearchDocFilesThenFindIt() {
        Search search = new Search();
        String root = this.testDirectory.getPath() + "/root";
        assertThat(search.files(root, Arrays.asList("doc")).size(), is(1110));
    }

    @Test
    public void whenSearchJpgTxtDocFilesThenFindIt() {
        Search search = new Search();
        String root = this.testDirectory.getPath() + "/root";
        assertThat(search.files(root, Arrays.asList("jpg", "txt", "doc")).size(), is(3330));
    }
}
