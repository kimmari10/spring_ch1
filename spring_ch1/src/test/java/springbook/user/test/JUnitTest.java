package springbook.user.test;

import static org.junit.Assert.*;
import static org.hamcrest .CoreMatchers.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JUnitTest {
	
	static JUnitTest testObject;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		assertThat(this, is(not(sameInstance(testObject))));
	}
	
	@Test
	public void test2() {
		assertThat(this, is(not(sameInstance(testObject))));
	}
	
	@Test
	public void test3() {
		assertThat(this, is(not(sameInstance(testObject))));
	}

}
