package patterns.abstractfactory.elven;

import patterns.abstractfactory.Army;
import patterns.abstractfactory.Castle;
import patterns.abstractfactory.King;
import patterns.abstractfactory.KingdomFactory;

public class ElfKingdomFactory implements KingdomFactory {

    @Override
    public Castle createCastle() {
        return new ElfCastle();
    }

    @Override
    public King createKing() {
        return new ElfKing();
    }

    @Override
    public Army createArmy() {
        return new ElfArmy();
    }
}
