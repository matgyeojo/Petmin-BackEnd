package org.matgyeojo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.matgyeojo.dto.Dolbom;
import org.matgyeojo.dto.PetProfile;
import org.matgyeojo.dto.PetsitterProfile;
import org.matgyeojo.dto.Preference;
import org.matgyeojo.dto.Review;
import org.matgyeojo.dto.Users;
import org.matgyeojo.repository.DolbomRepo;
import org.matgyeojo.repository.PetProfileRepo;
import org.matgyeojo.repository.PetsitterProfileRepo;
import org.matgyeojo.repository.PreferenceRepo;
import org.matgyeojo.repository.ReviewRepo;
import org.matgyeojo.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.java.Log;

@Service
public class DolbomService {
	@Autowired
	UsersRepo userrepo;
	@Autowired
	PetsitterProfileRepo petsitterrepo;
	@Autowired
	PetProfileRepo petrepo;
	@Autowired
	DolbomRepo dolbomrepo;
	@Autowired
	PreferenceRepo prerepo;
	@Autowired
	ReviewRepo reviewrepo;

	// 돌봄 선호필터
	public List<Object> dolbomFilter(String userId, String userAddress) {
		Users u = userrepo.findById(userId).orElse(null);// 유저 가져오기
		Preference pre = prerepo.findByUser(u);// 유저의 선호필터 가져오기
		// String userSex,int userAge,String sitterHousetype, String petSex,double
		// petWeight,String userAddress
		// 선호도 저장
		String userSex = pre.getPreference1();
		int userAge = pre.getPreference2();
		String sitterHousetype = pre.getPreference3();
		String petSex = pre.getPreference4();
		String petWeight = pre.getPreference5();
		if (userAddress.equals("")) {// 입력값에 아무것도 입력 안하면 기본으로 자신의 주소
			userAddress = u.getUserAddress();
		}
		
		List<String> filter4 = new ArrayList<>();
		if(petWeight.equals("소형견")) {
			int petweight = 10;
		filter4 = petrepo.findso(userAge, userSex, userAddress, sitterHousetype, petSex, petweight);
		}else if(petWeight.equals("중형견")) {
			int petweight = 25;
			filter4 = petrepo.findjoong(userAge, userSex, userAddress, sitterHousetype, petSex, petweight);		
		}else {
			int petweight = 25;
			filter4 = petrepo.findsdae(userAge, userSex, userAddress, sitterHousetype, petSex, petweight);
		}
		
		
		
		
		// 1.users 테이블 = 성별, 나이
		// 2.sitter테이블 = 집 타입
		// 3.펫 프로필 테이블 = 성별 , 몸무게 (소형견 : 10kg미만,중형견:10~25,대형견 25~)
		// 성별,나이,사는곳


		// List에 map을 저장
		List<Object> result = new ArrayList<>();

		List<String> options = new ArrayList<>();
		List<String> days = new ArrayList<>();

		// 만약 필터에 걸리는게 하나도 없으면
		if (filter4.isEmpty()) {
			// 유저 20개
			List<String> userss = userrepo.findUserAll();
			
			for (String use : userss) {
				HashMap<String, Object> map = new HashMap<>();
				Users user = userrepo.findById(use).orElse(null);
				PetsitterProfile sitter = petsitterrepo.findById(use).orElse(null);
	

				if (sitter != null) {
					// 프론트에서 필터링하는데 필요한 부분
					List<Dolbom> dol = dolbomrepo.findByUser2(user);

					for (Dolbom d : dol) {
						int count1 = 0;// 옵션 일치하는지
						int count2 = 0;
						for (String op : options) {// 옵션 가져오기
							if (d.getDolbomOption().equals(op)) {
								count1++;
							}
						}
						if (count1 == 0) {// 일치하는게 없으면
							options.add(d.getDolbomOption());

						} else {
							count1 = 0;
						}

						for (String da : days) {// 날짜 가져오기
							if (d.getScheduleDay().equals(da)) {
								count2++;
							}
						}
						if (count2 == 0) {// 일치하는게 없으면 추가
							days.add(d.getScheduleDay());
						} else {
							count2 = 0;
						}
					}

					// 리스트에 필요한 정보 추가.
					map.put("userName", user.getUserName());
					map.put("userAddress", user.getUserAddress());
					if (sitter.getSitterHouse() == null) {
						map.put("sitterHouse", "이미지 없음");
					} else {
						map.put("sitterHouse", sitter.getSitterHouse());
					}
					map.put("sitterMsg", sitter.getSitterMsg());
					map.put("sitterTem", sitter.getSitterTem());
					map.put("scheduleDay", days);
					map.put("dolbomOption", options);
					result.add(map);
				}
			}
			return result;
		} else {
			for (String fil : filter4) {
				// 필요한 정보들만 추출
				HashMap<String, Object> map = new HashMap<>();
				Users user = userrepo.findById(fil).orElse(null);
				PetsitterProfile sitter = petsitterrepo.findById(fil).orElse(null);

				if (sitter != null) {

					// 프론트에서 필터링하는데 필요한 부분
					List<Dolbom> dol = dolbomrepo.findByUser2(user);

					for (Dolbom d : dol) {
						int count1 = 0;// 옵션 일치하는지
						int count2 = 0;
						for (String op : options) {// 옵션 가져오기
							if (d.getDolbomOption().equals(op)) {
								count1++;
							}
						}
						if (count1 == 0) {// 일치하는게 없으면
							options.add(d.getDolbomOption());

						} else {
							count1 = 0;
						}

						for (String da : days) {// 날짜 가져오기
							if (d.getScheduleDay().equals(da)) {
								count2++;
							}
						}
						if (count2 == 0) {// 일치하는게 없으면 추가
							days.add(d.getScheduleDay());
						} else {
							count2 = 0;
						}
					}

					map.put("userName", user.getUserName());
					map.put("userAddress", user.getUserAddress());
					map.put("sitterHouse", sitter.getSitterHouse());
					map.put("sitterMsg", sitter.getSitterMsg());
					map.put("sitterTem", sitter.getSitterTem());
					map.put("scheduleDay", days);
					map.put("dolbomOption", options);
					result.add(map);
				}
			}
			return result;
		}
	}

	// 펫시터 자세히보기
	public List<Object> dolbomDetail(String sitterId) {
		// 유저 - userImg,userAddress,userName,userLicence,userAge,userSex
		// 펫시터 - sitterHouse,sitterHousetype,sitterMsg,sitterTem,
		// 펫 -petName,petSex,petMsg,petAge,petNo
		// 리뷰-petsitter,user,reviewMsg
		Users user = userrepo.findById(sitterId).orElse(null);
		PetsitterProfile sitter = petsitterrepo.findByUsers(user);
		List<PetProfile> pets = petrepo.findByUser(user);
		List<Review> reviews = reviewrepo.findByPetsitter(user);
		List<Dolbom> dolboms = dolbomrepo.findByUser2(user);

		// 리뷰 평균내기
		double time = 0;
		double kind = 0;
		double delecacy = 0;
		double allReview = 0;
		int count = 0;
		for (Review re : reviews) {
			time += re.getReviewTime();
			kind += re.getReviewKind();
			delecacy += re.getReviewDelecacy();
			count++;
		}
		time /= count;
		kind /= count;
		delecacy /= count;
		allReview = (time + kind + delecacy) / 3;

		List<Object> dolList = new ArrayList<>();

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("userImg", user.getUserImg());
		map.put("petsitter", sitter);
		map.put("pet", pets);
		map.put("reviewTime", time);
		map.put("reviewKind", kind);
		map.put("reviewDelecacy", delecacy);
		map.put("reviewAll", allReview);

		dolList.add(map);

		return dolList;
	}

	// 돌봄예약
	public int dolbomReservation(String userId, String sitterId, String scheduleDay, String[] scheduleHour,
			String petName) {
		int msg = 0;
		Users sitter = userrepo.findById(sitterId).orElse(null);// 펫시터 가져오기
		PetProfile pet = petrepo.findByPetName(petName);

		for (String s : scheduleHour) {// 스케쥴 시간 포문
			// 돌봄테이블에서 펫시터의 날짜 시간 일치하는거 가져와서 예약되었다고 표시.
			Dolbom dol = dolbomrepo.findByUser2AndScheduleDayAndScheduleHour(sitter, scheduleDay, s);
			dol.setDolbomStatus(true);
			dol.setPetProfile(pet);
			dolbomrepo.save(dol);
			msg = 1;
		}

		return msg;
	}

}
