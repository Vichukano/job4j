package ru.job4j.calculate;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
*Test
*@author Vichukano (vichukano@gmail.com)
*@version 1.0
*@since 10.08.2018
*/
public class CalculateTest {
	@Test
	public void whenTakeNameThenTreeEchoPlusName() {
		String input = "Vichukano";
		String expect = "Echo, echo, echo : Vichukano";
		Calculate calc = new Calculate();
		String result = calc.echo(input);
		assertThat(result, is(expect));
	}
}