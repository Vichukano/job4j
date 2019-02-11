package patterns.abstractfactory;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class KingdomFactoryTest {

    @Test
    public void elvenFactoryTest() {
        KingdomFactory factory = FactoryMaker.makeFactory(FactoryMaker.KingdomType.ELF);
        Army army = factory.createArmy();
        Castle castle = factory.createCastle();
        King king = factory.createKing();
        assertThat(army.getDesc(), is("Elven Army!"));
        assertThat(castle.getDesc(), is("Elf Castle!"));
        assertThat(king.getDesc(), is("Elf King Aegnor!"));
    }

    @Test
    public void orcishFactoryTest() {
        KingdomFactory factory = FactoryMaker.makeFactory(FactoryMaker.KingdomType.ORC);
        Army army = factory.createArmy();
        Castle castle = factory.createCastle();
        King king = factory.createKing();
        assertThat(army.getDesc(), is("Orc Army!"));
        assertThat(castle.getDesc(), is("Orc Castle!"));
        assertThat(king.getDesc(), is("Orc King Trerg!"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenIllegalFactoryThenThrowException() {
        KingdomFactory factory = FactoryMaker.makeFactory(FactoryMaker.KingdomType.valueOf("OGR"));
    }
}
