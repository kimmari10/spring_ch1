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

	public UserDao() {
		super();
	}
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setConnectionMaker(ConnectionMaker connectionMaker) {
		this.connectionMaker = connectionMaker;
	}

	public void add(User user) throws SQLException {
		StatementStrategy strategy = new AddStatement(user);
		jdbcContextWithStatementStrategy(strategy);
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
		StatementStrategy strategy = new DeleteAllStatement();
		jdbcContextWithStatementStrategy(strategy);
	}

	public void jdbcContextWithStatementStrategy(StatementStrategy strategy) throws SQLException {
		Connection c = null;
		PreparedStatement ps = null;
		try {
			//예외가 발생할 가능성이 있는 코드를 모두 try블록으로 묶어준다.
			c = dataSource.getConnection();
			ps = strategy.makePreparedStatement(c);
			ps.executeUpdate();
			
		} catch (SQLException e) {
			//예외가 발생했을 때 부가적인 작업을 해줄 수 있도록 catch 블록을 둔다. 
			//아직은 예외를 다시 메소드 밖으로 던지는 것 밖에 없다.
			throw e;
		} finally {	//finally이므로 try블록에서 예외가 발생했을 때나 안 했을 때나 모두 실행된다.
			if(ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					//ps.close() 에서도 SQLException이 발생 할 수 있기 때문에 이를 잡아줘야 한다. 그렇지 않으면 Connection을 close()하지 못하고 메소드를 빠져나갈 수 있다.
				}
			}
			if(c != null) {
				try {
					c.close();	//Connection반환
				} catch (SQLException e) {
				}
			}
		}
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
