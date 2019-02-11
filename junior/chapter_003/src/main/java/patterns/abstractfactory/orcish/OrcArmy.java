package patterns.abstractfactory.orcish;

import patterns.abstractfactory.Army;

public class OrcArmy implements Army {
    @Override
    public String getDesc() {
        return "Orc Army!";
    }
}
