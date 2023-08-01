package org.matgyeojo.controller;

import org.matgyeojo.dto.PetsitterProfile;
import org.matgyeojo.service.PetSitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sitter")
public class PetSitterController {

	@Autowired
	PetSitterService sitterService;
	
	@GetMapping(value = "/insert")
	public String petsitterInsert(@RequestParam String userId,@RequestParam String sitterHousetype,@RequestParam String sitterMsg  ) {
		 return sitterService.petsitterInsert(userId,sitterHousetype,sitterMsg);
	}
	
}
