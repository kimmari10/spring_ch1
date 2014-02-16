package springbook.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

public class JdbcContext {
	public DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void workWithStatementStrategy(StatementStrategy stmt) throws SQLException {
		Connection c = null;
		PreparedStatement ps = null;
		try {
			//예외가 발생할 가능성이 있는 코드를 모두 try블록으로 묶어준다.
			c = this.dataSource.getConnection();
			ps = stmt.makePreparedStatement(c);
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
}