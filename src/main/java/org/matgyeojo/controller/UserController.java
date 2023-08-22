package org.matgyeojo.controller;

import java.io.IOException;
import java.net.URL;

import org.matgyeojo.dto.Users;
import org.matgyeojo.repository.UsersRepo;
import org.matgyeojo.service.S3Uploader;
import org.matgyeojo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	UsersRepo userRepo;
	@Autowired
	private final S3Uploader s3Client;
	private final AmazonS3Client amazonS3Client;

	@GetMapping
	public Users getUserInfo(@RequestParam String userId) {
		return userService.getUserInfo(userId);
	}

	// 카드 등록
	@PutMapping(value = "/cardRegister")
	public Users registerCard(@RequestBody Users user) {
		return userService.registerCard(user);
	}
	
	//카드 비밀번호 확인
	@GetMapping(value = "/checkCard")
	public Boolean checkCard(@RequestParam String userId, String userCardPass) {
		return userService.checkCard(userId, Integer.parseInt(userCardPass));
	}

	// 개인정보 수정 - 주소, 사진
	@PutMapping(value = "/updateInfoAll")
	public Users updateInfoAll(@RequestParam String userId,@RequestParam String userAddress,
			@RequestParam String userDetailAddress, @RequestParam MultipartFile userImg) {
		Users user =userRepo.findById(userId).orElse(null);
		try {
			return userService.updateInfoAll(userId,userAddress,  userDetailAddress, userImg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
	// 개인정보 수정 - 주소
	@PutMapping(value = "/updateInfo")
	public Users updateInfo(@RequestBody Users user) {
		return userService.updateInfo(user);
 	}
}
