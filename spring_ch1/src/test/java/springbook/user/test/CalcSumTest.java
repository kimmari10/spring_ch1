package springbook.user.test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import springbook.learningtest.template.Calculator;

public class CalcSumTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void sumOfNumbers() throws IOException {
		Calculator calculartor = new Calculator();
		int sum = calculartor.calcSum(getClass().getResource("numbers.txt").getPath());
		assertThat(sum, is(10));
	}
 
}
