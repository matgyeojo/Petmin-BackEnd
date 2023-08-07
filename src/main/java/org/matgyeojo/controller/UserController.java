//package org.matgyeojo.controller;
//
//import org.matgyeojo.dto.Users;
//import org.matgyeojo.service.LoginService;
//import org.matgyeojo.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Conditional;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import lombok.RequiredArgsConstructor;
//
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/user")
//public class UserController {
//	
//	@Autowired
//	private UserService userService;
//	
//	//카드 등록
//	@PutMapping(value = "/cardRegister")
//	public Users registerCard(@RequestBody Users user){
//		return userService.registerCard(user);
//	}
//	
//	//개인정보 수정 - 주소
//	@PutMapping(value = "/updateAddress")
//	public Users updateAddress(@RequestBody Users user) {
//		return userService.updateAddress(user);
//	}
//}
