package patterns.abstractfactory.elven;

import patterns.abstractfactory.Army;

public class ElfArmy implements Army {

    @Override
    public String getDesc() {
        return "Elven Army!";
    }
}
