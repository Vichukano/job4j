package patterns.strategy;

public class OperationAdd implements CalculationStrategy {

    @Override
    public double execute(double x, double y) {
        return x + y;
    }
}
