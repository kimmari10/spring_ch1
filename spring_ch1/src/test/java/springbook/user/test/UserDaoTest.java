package springbook.user.test;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import springbook.user.dao.UserDaoJdbc;
import springbook.user.domain.User;

public class UserDaoTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void addAndGet() throws SQLException {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		UserDaoJdbc dao = context.getBean("userDao", UserDaoJdbc.class);
		User user = new User();
		user.setId("test");
		user.setName("김민섭");
		user.setPassword("1234");
		dao.add(user);
		
		User user2 = dao.get(user.getId());
		
		assertThat(user2.getName(), is(user.getName()));
		assertThat(user2.getPassword(), is(user.getPassword()));
	}
}
