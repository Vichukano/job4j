package parser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

/**
 * Класс описывает запуск приложения.
 * Имплементирует интерфейс Job.
 */
public class ParserJob implements Job {
    private static final String PROP_NAME = "app.properties";
    private static final Logger LOG = LogManager.getLogger(ParserJob.class);

    /**
     * Метод будет запускаться в определенное время по таймеру.
     * @param jobExecutionContext
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        Parser parser = new Parser();
        Map<Date, String> jobs = null;
        try {
            jobs = parser.getMsgFromLink();
        } catch (ParseException e) {
            LOG.error("Error ParseException", e);
        }
        StoreSQL sql = new StoreSQL(PROP_NAME);
        sql.addValuesToTable(jobs);
    }
}
