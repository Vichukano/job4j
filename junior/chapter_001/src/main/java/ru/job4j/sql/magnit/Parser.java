package ru.job4j.sql.magnit;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

/**
 * Класс описывает парсинг xml файла.
 */
public class Parser {
    private long sum;
    private File file;

    public Parser(File file) {
        this.file = file;
    }

    /**
     * Метод парсит все значения из xml с атрибутом href
     * и считает их сумму.
     * @return сумма значений.
     * @throws IOException
     * @throws SAXException
     */
    public long getSum() throws IOException, SAXException {
        DefaultHandler handler = new DefaultHandler() {
            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) {
                if (qName.equals("entry")) {
                    int value = Integer.parseInt(attributes.getValue("href"));
                    sum = sum + value;
                }
            }
        };
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            parser.parse(file, handler);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return sum;
    }
}
