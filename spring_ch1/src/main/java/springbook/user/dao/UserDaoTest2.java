package springbook.user.dao;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import springbook.user.domain.User;

public class UserDaoTest2 {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
//		ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);

		UserDaoJdbc dao = context.getBean("userDao", UserDaoJdbc.class);
		User user = new User();
		user.setId("whiteship2");
		user.setName("백기선2");
		user.setPassword("married");
		
		dao.add(user);
		
		System.out.println(user.getId() + "등록성공");
		
		User user2 = dao.get(user.getId());
	
		if(!user.getName().equals(user2.getName()))  {
			System.out.println("테스트 실패 (name)");
		}else if (!user.getPassword().equals(user2.getPassword())) {
			System.out.println("테스트 실패 (password)");
		}else {
			System.out.println("조회 테스트 성공");
		}
		
	}
}
