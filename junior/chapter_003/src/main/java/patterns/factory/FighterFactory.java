package patterns.factory;

public class FighterFactory {

    public Fighter getFighter(String type) {
        switch (type.toLowerCase()) {
            case "ufc":
                return new MmaFighter();
            case "wbc":
                return new BoxingFighter();
            case "glory":
                return new KickboxingFighter();
            default:
                return null;
        }
    }
}
