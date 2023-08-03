package org.matgyeojo.service;

import org.matgyeojo.dto.PetsitterProfile;
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
		
		Users user = UsersRepo.findById(dto.getUser().getUserId()).orElse(null);
		PetsitterProfile sitter = PetsitterProfile.builder().users(user).sitterTem(39.0).build();

		user.setPetsitterProfile(sitter);
		UsersRepo.save(user);//유저 밑에 시터가 있기때문에 유저를 저장
		PreferenceRepo.save(dto);
 	}
}
