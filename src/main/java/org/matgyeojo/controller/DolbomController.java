package org.matgyeojo.controller;

import java.util.List;

import org.matgyeojo.dto.Assurance;
import org.matgyeojo.dto.Dolbom;
import org.matgyeojo.dto.UserAssurance;
import org.matgyeojo.repository.UserAssuranceRepo;
import org.matgyeojo.service.DolbomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dolbom")
public class DolbomController {

	@Autowired
	DolbomService dolbomService;

	@Autowired
	UserAssuranceRepo userassuranceRepo;

	// 기본 선호필터 user랑 sitter정보만 + dolbom 스케쥴 날+ 돌봄옵션 뿌려주면 댐
	@GetMapping(value = "/filter")
	public List<Object> dolbomFilter(@RequestParam String userId, @RequestParam String userAddress) {
		return dolbomService.dolbomFilter(userId, userAddress);
	}

	// 돌봄 클릭햇을떄 디테일
	@GetMapping(value = "/detail")
	public List<Object> dolbomDetail(@RequestParam String sitterId) {
		return dolbomService.dolbomDetail(sitterId);
	}

	// 돌봄 예약
	@PostMapping(value = "/reservation")
	public int dolbomReservation(@RequestParam String userId, @RequestParam String sitterId,
			@RequestParam String[] scheduleDay, @RequestParam String[] scheduleHour, @RequestParam String petName) {
		return dolbomService.dolbomReservation(userId, sitterId, scheduleDay, scheduleHour, petName);
	}

	
	//돌봄 체크
	@GetMapping(value = "/checkSitter")
	public List<Object> dolbomCheckPetsitter(@RequestParam String userId){
		try {
			return dolbomService.dolbomCheckPetsitter(userId);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Object> result = new ArrayList<>();
		result.add("실패");
		return result;
	}
	
	//펫시터가 돌봄을 대기중->수락완료로 수락
	@PutMapping(value = "/surack")
	public String dolbomsurack(@RequestParam int dolbomNo) {
		return dolbomService.dolbomsurack(dolbomNo);
	}
	
}


	// 펫보험 신청 시 값 db에 들어가는거
	@PostMapping("/assurance")
	public ResponseEntity<String> reserveAssurance(@RequestParam Dolbom dolbomNo, Assurance assuranceName) {
		try {

			UserAssurance userAssurance = new UserAssurance();
			userAssurance.setAssuranceName(assuranceName);
			userAssurance.setDolbomNo(dolbomNo);
			userassuranceRepo.save(userAssurance);

			return ResponseEntity.ok("저장완료");
		} catch (Exception e) {
			return ResponseEntity.ok("저장실패");
		}
	}

}
