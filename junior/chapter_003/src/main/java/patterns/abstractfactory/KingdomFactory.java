package patterns.abstractfactory;

public interface KingdomFactory {

    Castle createCastle();

    King createKing();

    Army createArmy();

}
