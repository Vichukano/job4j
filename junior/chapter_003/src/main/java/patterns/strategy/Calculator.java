package patterns.strategy;

public class Calculator {
    private CalculationStrategy strategy;

    public Calculator() {

    }

    public Calculator(CalculationStrategy strategy) {
        this.strategy = strategy;
    }

    public double executeStrategy(double x, double y) {
        return this.strategy.execute(x, y);
    }

    public void setStrategy(CalculationStrategy strategy) {
        this.strategy = strategy;
    }
}
