package patterns.abstractfactory.elven;

import patterns.abstractfactory.Castle;

public class ElfCastle implements Castle {
    @Override
    public String getDesc() {
        return "Elf Castle!";
    }
}
