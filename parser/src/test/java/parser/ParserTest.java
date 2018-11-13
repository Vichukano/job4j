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
        assertThat(date.toString(), is("Fri Oct 19 14:32:00 MSK 2018"));
        date = parser.formatStringToDate("9 сен 17, 15:31");
        assertThat(date.toString(), is("Sat Sep 09 15:31:00 MSK 2017"));
        date = parser.formatStringToDate("28 май 18, 15:31");
        assertThat(date.toString(), is("Mon May 28 15:31:00 MSK 2018"));
    }

    @Test
    public void whenParseThenReturnListOfLinks() {
        Parser parser = new Parser();
        List<String> links = parser.getLinksFromForumTable();
        assertThat(links.size(), is(49 * 53));
        assertThat(links.get(0), is("https://www.sql.ru/forum/485068/soobshheniya-ot-moderatorov-zdes-vy-mozhete-uznat-prichiny-udaleniya-topikov"));
    }
}
