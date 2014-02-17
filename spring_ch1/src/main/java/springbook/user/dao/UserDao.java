package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;

import springbook.user.domain.User;

public class UserDao {
	private ConnectionMaker connectionMaker;
	private DataSource dataSource;
	private JdbcContext jdbcContext;
	
	public UserDao() {
		super();
	}
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setJdbcContext(JdbcContext jdbcContext) {
		this.jdbcContext = jdbcContext;
	}

	public void setConnectionMaker(ConnectionMaker connectionMaker) {
		this.connectionMaker = connectionMaker;
	}

	public void add(final User user) throws SQLException {
		this.jdbcContext.workWithStatementStrategy(new StatementStrategy() {
			public PreparedStatement makePreparedStatement(Connection c)
					throws SQLException {
				PreparedStatement ps = c.prepareStatement("insert into users(id, name, password) values(?,?,?)");
				ps.setString(1, user.getId());
				ps.setString(2, user.getName());
				ps.setString(3, user.getPassword());
				return ps;
			}
		});
	}

	public User get(String id) throws SQLException {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		User user = null;
		
		try {
			c = this.dataSource.getConnection();
			ps = c.prepareStatement("select * from users where id = ?");
			ps.setString(1, id);
			rs = ps.executeQuery();
			if(rs.next()) {
				user = new User();
				user.setId(rs.getString("id"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if(ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
				}
			}
			if(c != null) {
				try {
					c.close();
				} catch (SQLException e) {
				}
			}
		}
		
		if(user == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		return user;
	}
	
	public void deleteAll() throws SQLException {
		executeSql("delete from users");
	}

	private void executeSql(final String query) throws SQLException {
		this.jdbcContext.workWithStatementStrategy(new StatementStrategy() {
			public PreparedStatement makePreparedStatement(Connection c)
					throws SQLException {
				PreparedStatement ps = c.prepareStatement(query);
				return ps;
			}
		});
	}

	//메소드 추출 - 메소드를 다른 곳에 재사용 할 수 있어야 하는데, 이건 반대로 분리시키고  남은 메소드가 재사용이 필요한 부분이 되버렸다. 
	private PreparedStatement makeStatement(Connection c) throws SQLException {
		PreparedStatement ps;
		ps = c.prepareStatement("delete from users");
		return ps;
	}
	
	public int getCount() throws SQLException {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			c = dataSource.getConnection();
			ps = c.prepareStatement("select count(*) from users");
			rs = ps.executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			throw e;
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO: handle exception
				}
			}
			if(ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO: handle exception
				}
			}
			if(c != null) {
				try {
					c.close();
				} catch (SQLException e) {
					// TODO: handle exception
				}
			}
		}
	}
	
}
