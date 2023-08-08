package org.matgyeojo.controller;

import java.io.IOException;

import org.matgyeojo.dto.Users;
import org.matgyeojo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public Users getUserInfo(@RequestParam String userId) {
		return userService.getUserInfo(userId);
	}

	// 카드 등록
	@PutMapping(value = "/cardRegister")
	public Users registerCard(@RequestBody Users user) {
		return userService.registerCard(user);
	}

	// 개인정보 수정 - 주소, 사진
	@PutMapping(value = "/updateInfo")
	public Users updateInfo(@RequestBody Users user, @RequestParam MultipartFile userImg) {

		try {
			return userService.updateInfo(user, userImg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
}
