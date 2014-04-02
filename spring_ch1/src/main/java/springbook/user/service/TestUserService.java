package springbook.user.service;

import springbook.user.domain.User;

public class TestUserService extends UserServiceImpl {
	 private String id;
     
     TestUserService(String id) {
         this.id = id;
     }
     
     protected void upgradeLevel(User user) {
         if (user.getId().equals(this.id)) {
         	throw new TesUserServiceException();
         }
         super.upgradeLevel(user);
     }
}
