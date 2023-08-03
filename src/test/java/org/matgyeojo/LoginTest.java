package org.matgyeojo;

import org.junit.jupiter.api.Test;
import org.matgyeojo.dto.Users;
import org.matgyeojo.repository.UsersRepo;
import org.matgyeojo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LoginTest {
	
	@Autowired
	UsersRepo userrepo;
	
	@Test
	public void userAdd() {
 		Users user = Users.builder()
						  .userId("admin2")
						  .userPass("1234")
						  .userName("admin2")
						  .userAge(25)
						  .userAddress("Bucheon")
						  .userSex("f")
						  .userCard("12345-67892")
						  .userCardpass(1234)
						  .userLicence("실버펫시터")
						  .build();
		userrepo.save(user);
	}

}
