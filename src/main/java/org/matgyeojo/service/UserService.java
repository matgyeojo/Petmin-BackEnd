package org.matgyeojo.service;

import java.io.IOException;

import org.matgyeojo.dto.PetsitterProfile;
import org.matgyeojo.dto.Preference;
import org.matgyeojo.dto.Users;
import org.matgyeojo.repository.PreferenceRepo;
import org.matgyeojo.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService {
	@Autowired
	UsersRepo UsersRepo;

	@Autowired
	PreferenceRepo PreferenceRepo;

	@Autowired
	S3Uploader s3uploader;

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
	
	//카드 비밀번호 확인
	public Boolean checkCard(String userId, Integer userCardPass) {
		Users user = UsersRepo.findById(userId).orElse(null); 
		return user.getUserCardpass() - userCardPass == 0;
	}

	// 카드 등록
	public Users registerCard(Users users) {
		Users user = UsersRepo.findById(users.getUserId()).orElse(null);
		user.setUserCard(users.getUserCard());
		user.setUserCardpass(users.getUserCardpass());
		UsersRepo.save(user);
		return user;
	}

	// 개인정보 수정 - 주소, 사진
	public Users updateInfoAll(String userId, String userAddress,
				 String userDetailAddress,  MultipartFile userImg) throws IOException {
		Users user = UsersRepo.findById(userId).orElse(null);

		if (userImg != null && !userImg.isEmpty()) {
			String sotredFileName = s3uploader.upload(userImg, "user");
			user.setUserImg(sotredFileName);
		}
		user.setUserAddress(userAddress);
		user.setUserDetailAddress(userDetailAddress); 
		UsersRepo.save(user);
		return user;
	}
	
	// 개인정보 수정 - 주소
	public Users updateInfo(Users user) {
		Users users = UsersRepo.findById(user.getUserId()).orElse(null);
		users.setUserAddress(user.getUserAddress());
		users.setUserDetailAddress(user.getUserDetailAddress()); 	
		UsersRepo.save(users);
		return users;
	}

	// 유저 정보 얻기
	public Users getUserInfo(String userId) {
		return UsersRepo.findById(userId).orElse(null);
	}

	public String preferenceSave(Preference dto, String userId) {
		Users user = UsersRepo.findById(userId).orElse(null);
		if (user != null) {
			dto.setUser(user);
			PetsitterProfile sitter = PetsitterProfile.builder().users(user).sitterTem(39.0).build();

			user.setPetsitterProfile(sitter);
			UsersRepo.save(user);// 유저 밑에 시터가 있기때문에 유저를 저장
			PreferenceRepo.save(dto);
			return "선호도 저장 성공";
		} else {
			return "선호도 저장 실패";
		}

	}
}
