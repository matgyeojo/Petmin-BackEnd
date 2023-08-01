package org.matgyeojo.controller;

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
	
		@PostMapping(value = "/signup", consumes = "application/json")
		public void Signup(@RequestBody Users dto) {
			System.out.println(dto);
			UserService.signup(dto);
		}
		
		@GetMapping("/checkDuplicateId")
		public ResponseEntity<Boolean> checkDuplicateId(@RequestParam String userId) {
			return ResponseEntity.ok(UserService.checkDuplicateId(userId));
		}
//		   @GetMapping("/auth/checkDuplicateId")
//		   public ResponseEntity<String> checkDuplicateId(@RequestParam("memberid") String memberid) {
//		      // 데이터베이스에서 아이디 조회
//		      Optional<Members> existingMember = memberRepo.findByMemberid(memberid);
//
//		      if (existingMember.isPresent()) {
//		         // 중복된 아이디인 경우
//		         return ResponseEntity.ok("중복된아이디입니다.");
//		      } else {
//		         // 중복되지 않은 아이디인 경우
//		         return ResponseEntity.ok("사용가능한아이디입니다.");
//		      }
//		   }
		
	
}
