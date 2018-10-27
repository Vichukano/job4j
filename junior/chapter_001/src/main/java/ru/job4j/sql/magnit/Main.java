package ru.job4j.sql.magnit;

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
                    new File(Main.class.getClassLoader().getResource("entry.xml").getFile()),
                    new File(Main.class.getClassLoader().getResource("convert.xml").getFile()),
                    new File(Main.class.getClassLoader().getResource("scheme.xsl").getFile())
            );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Parser parser = new Parser(new File(Main.class.getClassLoader().getResource("convert.xml").getFile()));
        try {
            System.out.println(parser.getSum());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - start));
    }
}
