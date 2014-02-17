package springbook.user.test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import springbook.learningtest.template.Calculator;

public class CalcSumTest {
	
	Calculator calculator;
	String numFilepath;
	
	@Before
	public void setUp() throws Exception {
		this.calculator = new Calculator();
		this.numFilepath = getClass().getResource("numbers.txt").getPath();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void sumOfNumbers() throws IOException {
		assertThat(calculator.calcSum(this.numFilepath), is(10));
	}
	
	@Test
	public void calcMultiply() throws IOException {
		assertThat(calculator.calcMultiply(this.numFilepath), is(24));
	}
	
}
