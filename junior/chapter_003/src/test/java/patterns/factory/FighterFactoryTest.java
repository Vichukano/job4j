package patterns.factory;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class FighterFactoryTest {

    @Test
    public void whenFactoryCreateFighterThenReturnCorrectTypeOfFighter() {
        FighterFactory ff = new FighterFactory();
        Fighter mma = ff.getFighter("UFC");
        Fighter boxer = ff.getFighter("WBC");
        Fighter kickboxer = ff.getFighter("GLORY");
        assertThat(mma.readyToFight(), is("Mark Hunt is ready to fight!"));
        assertThat(boxer.readyToFight(), is("Tyson Fury is ready to fight!"));
        assertThat(kickboxer.readyToFight(), is("Jerome Le Banner is ready to fight!"));
    }

    @Test(expected = NullPointerException.class)
    public void whenFactoryCreateUnknownTypeOfFighterThenTrowException() {
        FighterFactory ff = new FighterFactory();
        Fighter unknown = ff.getFighter("KUNG-FU");
        assertThat(unknown, is(nullValue()));
        unknown.readyToFight();
    }
}
