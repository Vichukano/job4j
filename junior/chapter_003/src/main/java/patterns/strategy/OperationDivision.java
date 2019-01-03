package patterns.strategy;

public class OperationDivision implements CalculationStrategy {
    @Override
    public double execute(double x, double y) {
        return x / y;
    }
}
