package parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Главный класс приложения.
 */
public class Main {
    private static final String PROP_NAME = "app.properties";
    private static final Logger LOG = LogManager.getLogger(Main.class);


    /**
     * Метод принимает имя файла с настройками и на основе него создает объект properties.
     *
     * @param propName имя файла с настройками.
     * @return объект класса properties с загруженными настройками.
     */
    private Properties setProperties(String propName) {
        InputStream is;
        Properties properties = new Properties();
        try {
            is = Main.class.getClassLoader().getResourceAsStream(propName);
            LOG.debug("Настройки получены.");
            properties.load(is);
        } catch (IOException e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
        }
        return properties;
    }

    /**
     * Точка входа в программу.
     * В настройках cron.time = 0 0 12 * * ?
     * Данное выражение соответствует запуску приложения раз в 12 часов.
     *
     * @param args args.
     */
    public static void main(String[] args) {
        Main main = new Main();
        Properties prop = main.setProperties(PROP_NAME);
        JobDetail job = JobBuilder.newJob(ParserJob.class).build();
        Trigger trigger = TriggerBuilder.newTrigger()
                .startNow()
                .withIdentity("EveryDayTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule(prop.getProperty("cron.time")))
                .build();
        LOG.debug("Настройки триггера получены.");
        try {
            Scheduler sc = StdSchedulerFactory.getDefaultScheduler();
            sc.start();
            sc.scheduleJob(job, trigger);
            LOG.debug("Приложение в работе.");
        } catch (SchedulerException e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
