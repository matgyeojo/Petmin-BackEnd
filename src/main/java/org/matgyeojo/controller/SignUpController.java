package org.matgyeojo.controller;

import org.matgyeojo.dto.Preference;
import org.matgyeojo.dto.Users;
import org.matgyeojo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignUpController {
		@Autowired
		UserService UserService;
		
		//회원가입 : 회원정보 Users 테이블에 저장
		@PostMapping(value = "/signup", consumes = "application/json")
		public void Signup(@RequestBody Users dto) {
			System.out.println(dto);
			UserService.signup(dto);
		}
		
		//아이디 중복체크
		@GetMapping("/checkDuplicateId")
		public ResponseEntity<Boolean> checkDuplicateId(@RequestParam String userId) {
			return ResponseEntity.ok(UserService.checkDuplicateId(userId));
		}
		
		//회원가입 : Preference 테이블에 선호 정보 저장
		@PostMapping(value = "/preferenceSave", consumes = "application/json")
		public void preferenceSave(@RequestBody Preference dto ) {
			System.out.println(dto);
			System.out.println(dto.getUser().getUserId());
			UserService.preferenceSave(dto);
		}
		
		
		
}
