package org.matgyeojo.controller;

import java.io.IOException;

import org.matgyeojo.dto.PetsitterProfile;
import org.matgyeojo.service.PetSitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/sitter")
public class PetSitterController {

	@Autowired
	PetSitterService sitterService;
	

	@PostMapping(value = "/update")
	public ResponseEntity<?> petsitterUpdate(@RequestParam String userId,@RequestParam MultipartFile[] sitterHouse,@RequestParam String sitterHousetype,@RequestParam String sitterMsg ) {
		String user_id = null;
		try {
			user_id = sitterService.petsitterUpdate(userId, sitterHouse, sitterHousetype, sitterMsg);
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		return ResponseEntity.ok(user_id);
	}
	
	@PostMapping(value = "/insert")
	public ResponseEntity<?> petsitterInsert(@RequestParam String userId,@RequestParam MultipartFile[] sitterHouse,@RequestParam String sitterHousetype,@RequestParam String sitterMsg ) {
		String user_id = null;
		try {
			user_id = sitterService.petSitterInsert(userId,sitterHouse,sitterHousetype,sitterMsg);
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		return ResponseEntity.ok(user_id);
	}
	
}
