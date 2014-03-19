package springbook.user.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import springbook.user.domain.User;

public class UserDaoJdbc implements UserDao {
	private JdbcTemplate jdbcTemplate;
	
	public UserDaoJdbc() {
		super();
	}

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void add(final User user)  {
		this.jdbcTemplate.update("insert into users(id, name, password) values(?,?,?)", user.getId(), user.getName(), user.getPassword());
	}
	
	public void deleteAll()  {
		this.jdbcTemplate.update("delete from users");
	}

	public int getCount() {
		return this.jdbcTemplate.queryForObject("select count(*) from users", Integer.class);
	}

	public User get(String id) {
		return this.jdbcTemplate.queryForObject("select * from users where id = ?", new Object[] {id}, this.userMapper);
	}
	
	public List<User> getAll() {
		return this.jdbcTemplate.query("select * from users order by id", this.userMapper);
	}

	private RowMapper<User> userMapper = new RowMapper<User>(){
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
			return user;
		}
	};
	
}
