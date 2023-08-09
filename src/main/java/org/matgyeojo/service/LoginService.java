package org.matgyeojo.service;

import org.matgyeojo.dto.Users;
import org.matgyeojo.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LoginService {
	
	@Autowired
	UsersRepo usersRepo;
	
	//로그인
	public Users getUserById(Users users) {
		//id로 user 정보 찾기. 없으면 null
		Users user = usersRepo.findById(users.getUserId()).orElse(null);
		
		//null이 아니고, 비밀번호가 일치하다면 user 정보 return
		if(user != null && user.getUserPass().equals(users.getUserPass())) {
			return user;
		}else return null; 
	}

}
