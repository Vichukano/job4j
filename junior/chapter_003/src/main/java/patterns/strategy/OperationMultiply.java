package patterns.strategy;

public class OperationMultiply implements CalculationStrategy {
    @Override
    public double execute(double x, double y) {
        return x * y;
    }
}
