package ru.job4j.sql.magnit;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final String PATH = "config.properties";
    private static final String NAME = "url";

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        try {
            new StoreSQL(PATH, NAME, 1_000_000);
            new StoreXML(PATH, NAME);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ConvertXSQL convert = new ConvertXSQL();
        try {
            convert.convert(
                    new File("junior/chapter_001/src/main/java/ru/job4j/sql/magnit/resource/entry.xml"),
                    new File("junior/chapter_001/src/main/java/ru/job4j/sql/magnit/resource/convert.xml"),
                    new File("junior/chapter_001/src/main/java/ru/job4j/sql/magnit/resource/scheme.xsl")
            );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Parser parser = new Parser(new File("junior/chapter_001/src/main/java/ru/job4j/sql/magnit/resource/convert.xml"));
        try {
            System.out.println(parser.getSum());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        System.out.println(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - start));
    }
}
