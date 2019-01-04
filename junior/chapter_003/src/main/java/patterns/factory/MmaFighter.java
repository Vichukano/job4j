package patterns.factory;

public class MmaFighter implements Fighter {
    @Override
    public String readyToFight() {
        return "Mark Hunt is ready to fight!";
    }
}
