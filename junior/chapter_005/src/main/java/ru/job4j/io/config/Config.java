package ru.job4j.io.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Config {
    private final String path;
    private final Map<String, String> values = new HashMap<>();

    public Config(final String path) {
        this.path = path;
    }

    public void load() {
        try (BufferedReader reader = new BufferedReader(
                new FileReader(
                this.getClass().getClassLoader().getResource(this.path).getFile()
        ))) {
            reader.lines()
                    .filter(line -> !line.startsWith("##"))
                    .filter(line -> !line.isEmpty())
                    .map(line -> line.split("="))
                    .forEach(line -> this.values.put(line[0], line[1]));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String value(String key) {
        return this.values.get(key);
    }

    @Override
    public String toString() {
        StringJoiner out = new StringJoiner(System.lineSeparator());
        try (BufferedReader read = new BufferedReader(new FileReader(
                this.getClass().getClassLoader().getResource(this.path).getFile()
        ))) {
            read.lines().forEach(out::add);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out.toString();
    }

    public static void main(String[] args) {
        System.out.println(new Config("app.properties"));
    }
}
