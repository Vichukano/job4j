package patterns.factory;

public class BoxingFighter implements Fighter {
    @Override
    public String readyToFight() {
        return "Tyson Fury is ready to fight!";
    }
}
