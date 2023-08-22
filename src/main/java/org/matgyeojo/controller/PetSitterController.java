package org.matgyeojo.controller;

import java.io.IOException;
import java.util.List;

import org.matgyeojo.dto.PetsitterProfile;
import org.matgyeojo.repository.DolbomRepo;
import org.matgyeojo.repository.PetProfileRepo;
import org.matgyeojo.repository.UsersRepo;
import org.matgyeojo.service.PetSitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/sitter")
public class PetSitterController {

	@Autowired
	PetSitterService sitterService;
	@Autowired
	DolbomRepo dolbomrepo;
	@Autowired
	UsersRepo userrepo;
	@Autowired
	PetProfileRepo petrepo;

	// 펫시터 프로필 가져오기
	@GetMapping(value = "/getSitter")
	public PetsitterProfile getSitter(@RequestParam String userId) {
		return sitterService.getSitter(userId);
	}

	// 펫시터 일정 가져오기
	// 맵으로 보내주자 시간:스테이터스
	@GetMapping(value = "/getSchedule")
	public List<Object> getSchedure(@RequestParam String sitterId, @RequestParam String scheduleDay) {
		return sitterService.getSchedure(sitterId, scheduleDay);
	}

	// 펫시터 업데이트
	@PostMapping(value = "/update")
	public ResponseEntity<?> petsitterUpdate(@RequestParam String userId, @RequestParam MultipartFile[] sitterHouse,
			@RequestParam String sitterHousetype, @RequestParam String sitterMsg) {
		String user_id = null;

		try {
			user_id = sitterService.petsitterUpdate(userId, sitterHouse, sitterHousetype, sitterMsg);
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}

		return ResponseEntity.ok(user_id);
	}

	// 펫시터 이미지 없이 업데이트
	@PostMapping(value = "/update2")
	public ResponseEntity<?> petsitterUpdate2(@RequestParam String userId,@RequestParam String house, @RequestParam String sitterHousetype,
			@RequestParam String sitterMsg) {
		String user_id = null;

		user_id = sitterService.petsitterUpdate2(userId,house, sitterHousetype, sitterMsg);

		return ResponseEntity.ok(user_id);
	}

	//펫시터 실버등급업
	@PostMapping(value = "/licence")
	public String petsitterSilverLicence(@RequestParam String userId,@RequestParam int score) {
		
		return sitterService.petsitterSilverLicence(userId,score);
	}
	
	// 펫시터 생성
	@PostMapping(value = "/insert")
	public ResponseEntity<?> petsitterInsert(@RequestParam String userId, @RequestParam MultipartFile[] sitterHouse,
			@RequestParam String sitterHousetype, @RequestParam String sitterMsg) {
		String user_id = null;
		try {
			user_id = sitterService.petSitterInsert(userId, sitterHouse, sitterHousetype, sitterMsg);
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		return ResponseEntity.ok(user_id);
	}

	// 펫시터 스케쥴 등록
	@PostMapping(value = "/schedule")
	public int petsitterScadure(@RequestParam String sitterId, @RequestParam String scheduleDay,
			@RequestParam String[] scheduleHour, @RequestParam String dolbomOption) {

		return sitterService.petsitterScadure(sitterId, scheduleDay, scheduleHour, dolbomOption);
	}
}
