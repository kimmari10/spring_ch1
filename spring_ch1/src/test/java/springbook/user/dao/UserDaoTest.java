package springbook.user.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import springbook.user.domain.Level;
import springbook.user.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class UserDaoTest {
	
	@Autowired
	private UserDao dao;
	@Autowired
	private DataSource dataSource;
	
	private User user;
	private User user2;
	private User user3;
	
	@Before
	public void setUp() {
		this.user2 = new User("gyumee", "박성철", "springno2", Level.BASIC, 1, 0);
		this.user3 = new User("len1en", "김민섭", "springno1", Level.SILVER, 55, 10);
		this.user = new User("ggeeek", "박병은", "springno3", Level.GOLD, 100, 40);
	}
	
	@Test
	public void addAndGet() throws SQLException {
		
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.add(user);
		dao.add(user2);
		assertThat(dao.getCount(), is(2));
		
		User userget1 = dao.get(user.getId());
		checkSameUser(userget1, user);
		
		User userget2 = dao.get(user2.getId());
		checkSameUser(userget2, user2);
		
	}
	
	@Test
	public void count() throws SQLException {

		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.add(user);
		assertThat(dao.getCount(), is(1));
		
		dao.add(user2);
		assertThat(dao.getCount(), is(2));
		
		dao.add(user3);
		assertThat(dao.getCount(), is(3));
	}
	
	
	@Test(expected=EmptyResultDataAccessException.class)
	public void getUserFailure() throws SQLException {
		
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
			
		dao.get("unknown_id");
	}
	
	@Test
	public void getAll() throws SQLException {
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		//예외상황에 대한 테스트는 반드시 필요하다
		//아래의 리스트가 아무것도 없을 경우엔 query메소드에서 0을 리턴해주지만,
		//getAll에서 Exception을 낼수도 있고, query가 아닌 다른 방식으로 값을 가져오게 된다면, 다른 예외상황이 나타날 수 도 있기때문에
		//이런 예외에 대한 테스트는 항상 만들어 놓는 것이 좋다.
		List<User> users0 = dao.getAll();
		assertThat(users0.size(), is(0));
		
		List<User> userList = null;
		dao.add(user);
		userList = dao.getAll();
		assertThat(userList.size(), is(1));
		checkSameUser(user, userList.get(0));
		
		dao.add(user2);
		userList = dao.getAll();
		assertThat(userList.size(), is(2));
		checkSameUser(user, userList.get(0));
		checkSameUser(user2, userList.get(1));
		
		dao.add(user3);
		userList = dao.getAll();
		assertThat(dao.getCount(), is(3));
		checkSameUser(user, userList.get(0));
		checkSameUser(user2, userList.get(1));
		checkSameUser(user3, userList.get(2));
		
	}

	private void checkSameUser(User user4, User user5) {
		assertThat(user4.getId(), is(user5.getId()));
		assertThat(user4.getName(), is(user5.getName()));
		assertThat(user4.getPassword(), is(user5.getPassword()));
		assertThat(user4.getLevel(), is(user5.getLevel()));
		assertThat(user4.getLogin(), is(user5.getLogin()));
		assertThat(user4.getRecommend(), is(user5.getRecommend()));
	}
	
	@Test(expected=DuplicateKeyException.class)
	public void duplicateKey() {
		dao.deleteAll();
		dao.add(user);
		dao.add(user);
	}
	
	@Test
	public void sqlExceptionTranslate() {
		dao.deleteAll();
		try {
			dao.add(user);
			dao.add(user);
		} catch (DuplicateKeyException e) {
			// TODO: handle exception
			SQLException sqlEx = (SQLException)e.getRootCause();
			SQLExceptionTranslator set = new SQLErrorCodeSQLExceptionTranslator(this.dataSource);
			assertThat((DuplicateKeyException)set.translate(null, null, sqlEx), is(DuplicateKeyException.class));
		}
	}
}
