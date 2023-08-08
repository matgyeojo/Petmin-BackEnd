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
	
	public String signup(Users dto) {
		try {
			dto.setUserImg("https://petminbucket.s3.ap-northeast-2.amazonaws.com/user/%EC%9C%A0%EC%A0%80.png");
			dto.setUserLicence("일반");
			UsersRepo.save(dto);
			return "회원가입 성공";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "회원가입 실패";
		}
	}
	
 	//카드 등록
	public Users registerCard(Users users) {
		Users user =UsersRepo.findById(users.getUserId()).orElse(null);
		user.setUserCard(users.getUserCard());
		user.setUserCardpass(users.getUserCardpass());
		UsersRepo.save(user);
		return user;
	}
	
	//개인정보 수정 - 주소, 사진
	public Users updateInfo(Users users) {
		Users user = UsersRepo.findById(users.getUserId()).orElse(null);
		user.setUserAddress(users.getUserAddress());
		user.setUserImg(users.getUserImg());
		UsersRepo.save(user);
		return user;
	}
	
	//유저 정보 얻기
	public Users getUserInfo(String userId) {
		return UsersRepo.findById(userId).orElse(null);
	}
  

 	public String preferenceSave(Preference dto, String userId) {
		Users user = UsersRepo.findById(userId).orElse(null);
		if(user != null) {
			dto.setUser(user);
    	PetsitterProfile sitter = PetsitterProfile.builder().users(user).sitterTem(39.0).build();

		user.setPetsitterProfile(sitter);
		UsersRepo.save(user);//유저 밑에 시터가 있기때문에 유저를 저장
			PreferenceRepo.save(dto);
			return "선호도 저장 성공";
		} else {
			return "선호도 저장 실패";
		}

 	}
}
