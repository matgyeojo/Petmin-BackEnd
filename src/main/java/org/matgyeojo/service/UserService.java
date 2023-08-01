package org.matgyeojo.service;

import org.matgyeojo.dto.Preference;
import org.matgyeojo.dto.Users;
import org.matgyeojo.repository.PreferenceRepo;
import org.matgyeojo.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	@Autowired
	UsersRepo UsersRepo;
	
	@Autowired
	PreferenceRepo PreferenceRepo;
	
	public boolean checkDuplicateId(String userId) {
		return UsersRepo.existsByUserId(userId);
	}
	
	public void signup(Users dto) {
		UsersRepo.save(dto);
	}
	
 	//카드 등록
	public Users registerCard(Users users) {
		Users user =UsersRepo.findById(users.getUserId()).orElse(null);
		user.setUserCard(users.getUserCard());
		user.setUserCardpass(users.getUserCardpass());
		UsersRepo.save(user);
		return user;
}
  
 	public void preferenceSave(Preference dto) {
		Users userId = UsersRepo.findById(dto.getUser().getUserId()).get();
		dto.setUser(userId);
		PreferenceRepo.save(dto);
 	}
}
