package patterns.abstractfactory.elven;

import patterns.abstractfactory.King;

public class ElfKing implements King {
    @Override
    public String getDesc() {
        return "Elf King Aegnor!";
    }
}
