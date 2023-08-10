package org.matgyeojo.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.matgyeojo.dto.PetProfile;
import org.matgyeojo.dto.PetTendency;
import org.matgyeojo.dto.PetVaccine;
import org.matgyeojo.dto.Users;
import org.matgyeojo.service.PetProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class PetProfileController {
	@Autowired
	PetProfileService PetProfileService;
	
	//반려동물 등록 페이지 : 반려동물 정보 Pet profile 테이블에 저장
	@PostMapping("/petProfileSave")
	public ResponseEntity<String> petprofileSave(
			@RequestParam String userId, 
			@RequestParam String petName, 
			@RequestParam int petAge,
			@RequestParam String petSpecies,
			@RequestParam double petWeight,
			@RequestParam String petSex,
			@RequestParam MultipartFile petImg,
			@RequestParam String petMsg
			) {
		String message = null;
		try {
			message = PetProfileService.petprofileSave(userId, petName, petAge, petSpecies, petWeight, petSex, petImg, petMsg);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(message);
		
	}
	
	//반려동물 등록 페이지 : 반려동물 성향 Pet tendency 테이블에 저장
	@PostMapping("/petTendencySave/{petNo}")
	public ResponseEntity<String> petTendencySave(@RequestBody PetTendency dto, @PathVariable Integer petNo) {
		String message = PetProfileService.petTendencySave(dto, petNo);
		return ResponseEntity.ok(message);
	}
	
	//반려동물 등록 페이지 : 반려동물 예방접종 정보 PET_VACCINE 테이블에 저장
	@PostMapping("/petVaccineSave/{petNo}")
	public ResponseEntity<String> petVaccineSave(@RequestBody PetVaccine dto, @PathVariable Integer petNo) {
		String message = PetProfileService.petVaccineSave(dto, petNo);
		return ResponseEntity.ok(message);
	}
	
	//해당 아이디의 반려동물 리스트(주인과 다른 사람이 볼 때 동일)
	@GetMapping(value = "/petProfileList/{userId}")
	public List<PetProfile> petProfileList(@PathVariable String userId) {
		return PetProfileService.petProfileList(userId);
	}
	
	//각각의 반려동물 프로필 조회 (주인은 조회 + 수정이 가능한 페이지 / 다른 사람은 조회만 가능하게 프론트에서 나누면 될 듯)
	@GetMapping(value = "/petInformationRead/{petNo}")
	public Optional<PetProfile> petInformation(@PathVariable Integer petNo) {
		return PetProfileService.petInformation(petNo);
	}
	
	//반려동물 프로필 수정
	@PutMapping("/petInformationUpdate/{petNo}")
	public ResponseEntity<String> petInformationUpdate(@RequestBody PetProfile dto, @PathVariable Integer petNo) {
		String message = PetProfileService.petInformationUpdate(dto, petNo);
		return ResponseEntity.ok(message);
	}
	
	//반려동물 예방접종 정보 수정
	@PutMapping("/petVaccineUpdate/{vaccineNo}")
	public ResponseEntity<String> petVaccineUpdate(@RequestBody PetVaccine dto, @PathVariable Integer vaccineNo) {
		String message = PetProfileService.petVaccineUpdate(dto, vaccineNo);
		return ResponseEntity.ok(message);
	}
	
	//반려동물 성향 수정(해당 주인만 수정할 수 있음)
	@PutMapping("/petTendencyUpdate/{tendenctNo}")
	public ResponseEntity<String> petTendencyUpdate(@RequestBody PetTendency dto, @PathVariable Integer tendenctNo) {
		String message = PetProfileService.petTendencyUpdate(dto, tendenctNo);
		return ResponseEntity.ok(message);
	}
	
	//반려동물 프로필 삭제
	@DeleteMapping("/petInformainDelete/{petNo}")
	public ResponseEntity<String> petInformainDelete(@PathVariable Integer petNo) {
		String message = PetProfileService.petInformainDelete(petNo);
		return ResponseEntity.ok(message);
	}
}
