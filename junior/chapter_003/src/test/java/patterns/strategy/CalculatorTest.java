package patterns.strategy;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CalculatorTest {

    @Test
    public void whenAddStrategyThenReturnSumOfArguments() {
        Calculator calculator = new Calculator();
        calculator.setStrategy(new OperationAdd());
        assertThat(calculator.executeStrategy(1.0, 2.0), is(3.0));
    }

    @Test
    public void whenSubtractStrategyThenReturnSubtractOfArguments() {
        Calculator calculator = new Calculator(new OperationSubtract());
        assertThat(calculator.executeStrategy(2.0, 1.0), is(1.0));
    }

    @Test
    public void whenDivisionStrategyThenReturnDivisionOfArguments() {
        Calculator calculator = new Calculator();
        calculator.setStrategy(new OperationDivision());
        assertThat(calculator.executeStrategy(3.0, 2.0), is(1.5));
    }

    @Test
    public void whenMultiplyStrategyThenReturnMultiplyOfArguments() {
        Calculator calculator = new Calculator();
        calculator.setStrategy(new OperationMultiply());
        assertThat(calculator.executeStrategy(2.0, 2.0), is(4.0));
    }

}
