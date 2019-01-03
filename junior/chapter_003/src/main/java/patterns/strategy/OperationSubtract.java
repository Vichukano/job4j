package patterns.strategy;

public class OperationSubtract implements CalculationStrategy {
    @Override
    public double execute(double x, double y) {
        return x - y;
    }
}
