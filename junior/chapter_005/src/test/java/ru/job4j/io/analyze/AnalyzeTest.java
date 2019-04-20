package ru.job4j.io.analyze;

import org.junit.Test;
import ru.job4j.io.config.Config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.StringJoiner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AnalyzeTest {

    @Test
    public void whenAnalyzeThenStoreItInNewFile() {
        Analyze analyze = new Analyze(new Config("app.properties"));
        TestReader reader = new TestReader();
        analyze.unavailable("analyze.log", "unavailable.csv");
        assertThat(reader.readSource("unavailable.csv"),
                is(String.format(
                        "%s%s%s",
                        "10:57:01;10:59:01;",
                        System.lineSeparator(),
                        "11:01:02;11:02:02;"))
        );
    }

    /**
     * Inner class for testing output.
     */
    private class TestReader {

        /**
         * Method for reading source file.
         *
         * @param source file.
         * @return text of file.
         */
        public String readSource(String source) {
            StringJoiner out = new StringJoiner(System.lineSeparator());
            try (BufferedReader reader = new BufferedReader(
                    new FileReader(source)
            )) {
                reader.lines().forEach(out::add);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return out.toString();
        }
    }
}
