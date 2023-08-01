package org.matgyeojo.controller;

import java.io.IOException;

import org.matgyeojo.service.PetService;
import org.matgyeojo.service.S3Uploader;
import org.matgyeojo.service.S3Uploader2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/pet")
public class PetController {

	@Autowired
	PetService petService;
	@Autowired
	S3Uploader s3Uploader;
	@Autowired
	S3Uploader2 s3Uploader2;
	
	
	@PostMapping(value = "/insert")
	public ResponseEntity<?> petInsert(@RequestParam String userId,@RequestParam String petName,@RequestParam MultipartFile[] petImgs ) {
		int pet_id = 0;
		
		try {
			pet_id = petService.petInsert(userId, petName, petImgs);
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		
		return ResponseEntity.ok(pet_id);
	}
}
