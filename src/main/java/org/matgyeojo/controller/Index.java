package org.matgyeojo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Index {
	@GetMapping("/test")
	public String Nowij() {
		System.out.println("컨트롤러 오는지");
		return "Spring Boot and React 연동 테스트";
	}
	
}
	

