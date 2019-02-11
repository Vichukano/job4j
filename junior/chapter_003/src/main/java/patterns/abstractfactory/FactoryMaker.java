package patterns.abstractfactory;

import patterns.abstractfactory.elven.ElfKingdomFactory;
import patterns.abstractfactory.orcish.OrcKingdomFactory;

public class FactoryMaker {

    public enum KingdomType {
        ELF,
        ORC
    }

    public static KingdomFactory makeFactory(KingdomType type) {
        switch (type) {
            case ELF:
                return new ElfKingdomFactory();
            case ORC:
                return new OrcKingdomFactory();
            default:
                throw new IllegalArgumentException("KingdomType does not supported!");
        }
    }
}
