package parser;

import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ParserTest {

    @Test
    public void whenAddStringItShouldConvertToDate() {
        Parser parser = new Parser();
        Date date = parser.formatStringToDate("19 окт 18, 14:32");
        String dateToString = date.toString().replace("MSK 2018", "");
        assertThat(dateToString, is("Fri Oct 19 14:32:00 "));
        date = parser.formatStringToDate("9 сен 17, 15:31");
        dateToString = date.toString().replace("MSK 2017", "");
        assertThat(dateToString, is("Sat Sep 09 15:31:00 "));
        date = parser.formatStringToDate("28 май 18, 15:31");
        dateToString = date.toString().replace("MSK 2018", "");
        assertThat(dateToString, is("Mon May 28 15:31:00 "));
    }

    @Test
    public void whenParseThenReturnListOfLinks() {
        Parser parser = new Parser();
        List<String> links = parser.getLinksFromForumTable();
        assertThat(links.size(), is(49 * 53));
        assertThat(links.get(0), is("http://www.sql.ru/forum/485068/soobshheniya-ot-moderatorov-zdes-vy-mozhete-uznat-prichiny-udaleniya-topikov"));
    }
}
