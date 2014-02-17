package springbook.user.test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.BufferedReader;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import springbook.learningtest.template.BufferedReaderCallback;
import springbook.learningtest.template.Calculator;

public class CalcSumTest {
	
	Calculator calculator;
	
	@Before
	public void setUp() throws Exception {
		calculator = new Calculator();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void sumOfNumbers() throws IOException {
		BufferedReaderCallback sumCallback = new BufferedReaderCallback() {
			public Integer doSomethingWithReader(BufferedReader br) throws IOException {
					Integer sum = 0;
					String line = null;
					while((line = br.readLine()) != null) {
						sum += Integer.valueOf(line);
					}
					return sum;
			}
		};
		int sum = calculator.fileReadTemplate(getClass().getResource("numbers.txt").getPath(),sumCallback);
		assertThat(sum, is(10));
	}
 
}
