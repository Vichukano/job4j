package parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Класс описывает получение информации о вакансиях с сайта www.sql.ru
 * Скачиваются только вакансии, в которых требуются java разработчики.
 * Информация о вакансиях скачивается с первых 30 страниц форума.
 * Реализован отбор вакансий только за 2018 год.
 * Текс вакансии и дата создания объявления сохраняются в Map.
 * Для парсинга сайта используется бибилиотека jsoup.
 */
public class Parser {
    private static final Logger LOG = LogManager.getLogger(Parser.class);
    private static final Marker SQL_MARKER = MarkerManager.getMarker("SQL");
    /**
     * Регулярное выражение, по кторому идет отбор текста вакансий.
     * Пропускает java, Java, JAVA, java ee, java8 и тд.
     * Не пропустит java script и различные вариации.
     */
    private final String regex = "((\\bjava..[^cCrRiIpPtT])|(\\bJAVA..[^cCrRiIpPtT])|(\\bJava..[^cCrRiIpPtT]))";
    private final String path = "http://www.sql.ru/forum/job-offers/";

    public Parser() {

    }

    /**
     * Метод получает ссылки на вакансии, которые собираются с форума.
     *
     * @return список ссылок.
     */
    public List<String> getLinksFromForumTable() {
        List<String> links = new ArrayList<>();
        int count = 0;
        for (int i = 1; i != 50; i++) {
            String url = String.format("%s%s", path, i);
            try {
                Document doc = Jsoup.connect(url).get();
                LOG.debug("Получили подключение к сайту." + " Парсим страницу " + i);
                Elements forumTable = doc.select("table.forumTable");
                for (Element element : forumTable.select("td.postslisttopic")) {
                    links.add(element.select("a").attr("href"));
                    count++;
                }
            } catch (IOException e) {
                LOG.error("Error IOException", e);
            }
        }
        LOG.debug(SQL_MARKER, "Inserted {} jobs", count);
        return links;
    }

    /**
     * Метод получает список ссылок с вакансиями, извекает из каждой ссылки
     * текст вакансии и дату создания и помещает в словарь. Ключем является дата создания.
     * Получение даты создание реализовано через конвертирование текста в объект Date.
     * Текст вакансий проверяется на соответствие регулярному выражению и в случае проверки добавляется в Map.
     *
     * @return map с ключем - датой создания вакансии и значением - текстом вакансии.
     */
    public HashMap<Date, String> getMsgFromLink() throws ParseException {
        List<String> links = getLinksFromForumTable();
        HashMap<Date, String> messages = new HashMap<>();
        int count = 0;
        for (String link : links) {
            try {
                Document doc = Jsoup.connect(link).get();
                LOG.debug("Получили подключение к ссылке: " + link);
                Element tableElement = doc.selectFirst("table.msgTable");
                String createdDate = tableElement.select("td.msgFooter").text();
                createdDate = createdDate.substring(0, createdDate.indexOf('['));
                Date date = formatStringToDate(createdDate);
                String msg = tableElement.select("td.msgBody").get(1).text();
                Matcher javaMatcher = Pattern.compile(regex).matcher(msg);
                if (date.after(
                        new SimpleDateFormat("dd MMM yy").parse("01 янв 18"))
                        && javaMatcher.find()
                        ) {
                    messages.put(date, msg);
                    count++;
                    LOG.debug("Вакансия добавлена");
                }
            } catch (IOException e) {
                LOG.error("Error IOException", e);
            }
        }
        LOG.debug(SQL_MARKER, "Added {} jobs", count);
        return messages;
    }

    /**
     * Метод реализует преобразование текста с датой создания вакансии в объект класса Date.
     * Реализована возможность преобратозование в Date строк типа "вчера, сегодня"
     *
     * @param createdDate строка с датой создания.
     * @return объект Date, соответствующий дате создания.
     */
    public Date formatStringToDate(String createdDate) {
        LocalTime timePart = null;
        LocalDate datePart = null;
        String regex = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
        Matcher m = Pattern.compile(regex).matcher(createdDate);
        if (m.find()) {
            timePart = LocalTime.parse(m.group(0));
        }
        if (createdDate.contains("сегодня")) {
            datePart = LocalDate.now();
        } else if (createdDate.contains("вчера")) {
            datePart = LocalDate.now().minus(Period.ofDays(1));
        } else {
            createdDate = createdDate.substring(0, createdDate.indexOf(","));
            if (createdDate.contains("май")) {
                createdDate = createdDate.replace("май", "мая");
            }
            if (createdDate.length() == 8) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yy").withLocale(new Locale("ru"));
                datePart = LocalDate.parse(createdDate, formatter);
            } else {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yy").withLocale(new Locale("ru"));
                datePart = LocalDate.parse(createdDate, formatter);
            }
        }
        LocalDateTime date = LocalDateTime.of(datePart, timePart);
        return Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
    }
}

