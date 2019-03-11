package patterns.decorator;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class DecoratorTest {

    @Test
    public void decoratorTest() {
        Developer junior = new JavaDeveloper();
        Developer senior = new SeniorJavaDeveloper(new JavaDeveloper());
        Developer teamLead = new TeamLeadJavaDeveloper(new SeniorJavaDeveloper(new JavaDeveloper()));
        assertThat(junior.work(), is("Write java code"));
        assertThat(senior.work(), is("Write java code Make code review"));
        assertThat(teamLead.work(), is("Write java code Make code review Be serious"));
    }
}
