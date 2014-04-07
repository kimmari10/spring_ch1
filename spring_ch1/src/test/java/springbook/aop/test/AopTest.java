package springbook.aop.test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

import java.lang.reflect.Proxy;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AopTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void simpleProxy() {
		Hello hello = new HelloTarget();
		assertThat(hello.sayHello("Toby"), is("Hello Toby"));
		assertThat(hello.sayHi("Toby"), is("Hi Toby"));
		assertThat(hello.sayThankYou("Toby"), is("Thank You Toby"));
	}
	
	
	@Test
	public void upperProxy() {
		Hello hello = new HelloUppercase(new HelloTarget());
		assertThat(hello.sayHello("Toby"), is("HELLO TOBY"));
		assertThat(hello.sayHi("Toby"), is("HI TOBY"));
		assertThat(hello.sayThankYou("Toby"), is("THANK YOU TOBY"));
	}
	
	@Test
	public void dynamicProxy() {
		Hello proxiedHello = (Hello)Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] {Hello.class}, new UppercaseHandler(new HelloTarget()));
		assertThat(proxiedHello.sayHello("Toby"), is("HELLO TOBY"));
		assertThat(proxiedHello.sayHi("Toby"), is("HI TOBY"));
		assertThat(proxiedHello.sayThankYou("Toby"), is("THANK YOU TOBY"));
	}

}
