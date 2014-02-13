package springbook.user.test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JUnitTest {
	
	static Set<JUnitTest> testObjects = new HashSet<JUnitTest>();
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		assertThat(testObjects, is(not(hasItem(this))));
		testObjects.add(this);
	}
	
	@Test
	public void test2() {
		assertThat(testObjects, is(not(hasItem(this))));
		testObjects.add(this);
	}
	
	@Test
	public void test3() {
		assertThat(testObjects, is(not(hasItem(this))));
		testObjects.add(this);
	}

}
