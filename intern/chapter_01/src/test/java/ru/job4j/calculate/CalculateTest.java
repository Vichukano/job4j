package ru.job4j.calculate;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

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

	@Test
    public void whenCallMainThenPrintHelloWorld() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        Calculate.main(null);
        assertThat(out.toString(), is("Hello World!\r\n"));
    }
}