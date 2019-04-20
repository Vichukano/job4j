package ru.job4j.io.analyze;

import ru.job4j.io.config.Config;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * Class for analyzing server log file.
 */
public class Analyze {
    private boolean flag = true;
    private Config config;

    public Analyze(Config config) {
        this.config = config;
        this.config.load();
    }

    /**
     * Method read server log file, analyze unavailable time
     * and read it to target csv file.
     * Unavailable server statuses store it app.properties file
     * and load by Config object{@link Config#value(String)}
     *
     * @param source server log file
     * @param target file with results
     */
    public void unavailable(String source, String target) {
        String[] statuses = this.config.value("status.unavailable").split(",");
        try (BufferedReader reader = new BufferedReader(
                new FileReader(
                        this.getClass().getClassLoader().getResource(source).getFile()
                )
        );
             PrintWriter out = new PrintWriter(
                     new FileOutputStream(target)
             )
        ) {
            StringBuilder s = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                if (flag) {
                    if (this.isContainsStatus(line, statuses)) {
                        s.append(String.format("%s;", line.substring(4)));
                        flag = false;
                    }
                }
                if (!flag) {
                    if (!isContainsStatus(line, statuses) && !line.isEmpty()) {
                        s.append(String.format("%s;", line.substring(4)));
                        s.append(System.lineSeparator());
                        flag = true;
                    }
                }
            }
            out.print(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method for checking status in line of
     * server log file text.
     *
     * @param line     line of file.
     * @param statuses server unavailable statuses.
     * @return true if status contains in line, else false.
     */
    private boolean isContainsStatus(String line, String[] statuses) {
        return Arrays.stream(statuses).anyMatch(line::startsWith);
    }
}
