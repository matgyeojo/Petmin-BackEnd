package org.matgyeojo.service;

import org.matgyeojo.dto.Users;
import org.matgyeojo.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	@Autowired
	UsersRepo UsersRepo;
	
	public boolean checkDuplicateId(String usernickname) {
		return UsersRepo.existsByUserNickname(usernickname);
	}
	
	public void signup(Users dto) {
		UsersRepo.save(dto);
	}
}
