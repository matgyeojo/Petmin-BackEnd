package org.matgyeojo.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.matgyeojo.dto.Assurance;
import org.matgyeojo.dto.Dolbom;
import org.matgyeojo.dto.Review;
import org.matgyeojo.dto.UserAssurance;
import org.matgyeojo.repository.AssuranceRepo;
import org.matgyeojo.repository.DolbomRepo;
import org.matgyeojo.repository.UserAssuranceRepo;
import org.matgyeojo.service.DolbomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	@Autowired
	AssuranceRepo assuarancerepo;
	@Autowired
	DolbomRepo dolbomrepo;

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

//	// 돌봄 예약
//	@PostMapping(value = "/reservation")
//	public int dolbomReservation(@RequestParam String userId, @RequestParam String sitterId,
//			@RequestParam String[] scheduleDay, @RequestParam String[] scheduleHour, @RequestParam String petName) {
//		return dolbomService.dolbomReservation(userId, sitterId, scheduleDay, scheduleHour, petName);
//	}
	// 돌봄 예약
		@PostMapping(value = "/reservation")
		public int dolbomReservation(@RequestParam String userId, @RequestParam String sitterId,
				@RequestParam String[] scheduleDay, @RequestParam String petName) {
			return dolbomService.dolbomReservation(userId, sitterId, scheduleDay, petName);
		}


	// 돌봄 펫시터입장에서 체크
	@GetMapping(value = "/checkSitter")
	public List<Object> dolbomCheckPetsitter(@RequestParam String userId) {
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

	// 펫시터가 돌봄을 대기중->수락완료로 수락
	@PutMapping(value = "/surack")
	public String dolbomsurack(@RequestParam int dolbomNo) {
		return dolbomService.dolbomsurack(dolbomNo);
	}

	// 돌봄 체크
	@GetMapping(value = "/checkUser")
	public List<Object> dolbomCheckUser(@RequestParam String userId) {
		try {
			return dolbomService.dolbomCheckuser(userId);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Object> result = new ArrayList<>();
		result.add("실패");
		return result;
	}
	
	//돌봄 삭제
	@DeleteMapping(value = "/delete")
	public String dolbomDelete(@RequestParam int dolbomNo) {
		return dolbomService.dolbomDelete(dolbomNo);
	}

	// 펫시터 종료 후 리뷰쓰기

	// 펫보험 신청 시 값 db에 들어가는거
	@PostMapping("/assurance")
	public ResponseEntity<String> reserveAssurance(@RequestParam int dolbomNo, @RequestParam String assuranceName) {
		try {
			Dolbom dolbom = dolbomrepo.findById(dolbomNo).orElse(null);
			Assurance assuarance = assuarancerepo.findByAssuranceName(assuranceName);

			// 현재 시간이랑 비교해 돌봄 상태 변경
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date sDate = sdf.parse(dolbom.getSTART_CARE());
			Date eDate = sdf.parse(dolbom.getEND_CARE());

			int x = assuarance.getAssurancePrice();
			int day = eDate.getDate() - sDate.getDate();
			int hour = eDate.getHours() - sDate.getHours();
			if (hour < 0) {
				day -= 1;
				hour += 24;
			}
			int won = (24 * x * day) + (hour * x);

			UserAssurance userAssurance = UserAssurance.builder().assurance(assuarance).dolbom(dolbom).build();
			userassuranceRepo.save(userAssurance);

			return ResponseEntity.ok(won + "");
		} catch (Exception e) {
			return ResponseEntity.ok("저장실패");
		}
	}
	//리뷰 전체목록
	@GetMapping(value = "/reviewList")
	public List<Object> reviewList(@RequestParam String sitterId){
		return dolbomService.reviewList(sitterId);
	}
	
	//리뷰등록
	@PostMapping(value = "/inReview")
	public String inReview(@RequestParam int dolbomNo,@RequestParam int reviewTime,
			@RequestParam int reviewKind,@RequestParam int reviewDelecacy,@RequestParam String reviewMsg) {
		return dolbomService.inReview(  dolbomNo,  reviewTime,
				  reviewKind,  reviewDelecacy,  reviewMsg);
	}

}