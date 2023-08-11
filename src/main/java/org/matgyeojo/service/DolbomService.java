package org.matgyeojo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.matgyeojo.dto.Alarm;
import org.matgyeojo.dto.Dolbom;
import org.matgyeojo.dto.PetProfile;
import org.matgyeojo.dto.PetsitterProfile;
import org.matgyeojo.dto.Preference;
import org.matgyeojo.dto.Review;
import org.matgyeojo.dto.Users;
import org.matgyeojo.repository.AlarmRepo;
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
	@Autowired
	AlarmRepo alrepo;

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
		if (petWeight.equals("소형견")) {
			int petweight = 10;
			filter4 = petrepo.findso(userAge, userSex, userAddress, sitterHousetype, petSex, petweight);
		} else if (petWeight.equals("중형견")) {
			int petweight = 25;
			filter4 = petrepo.findjoong(userAge, userSex, userAddress, sitterHousetype, petSex, petweight);
		} else {
			int petweight = 25;
			filter4 = petrepo.findsdae(userAge, userSex, userAddress, sitterHousetype, petSex, petweight);
		}

		// 1.users 테이블 = 성별, 나이
		// 2.sitter테이블 = 집 타입
		// 3.펫 프로필 테이블 = 성별 , 몸무게 (소형견 : 10kg미만,중형견:10~25,대형견 25~)
		// 성별,나이,사는곳

		// List에 map을 저장
		List<Object> result = new ArrayList<>();

		HashMap<String, String> success = new HashMap<String, String>();

		// 만약 필터에 걸리는게 하나도 없으면
		if (filter4.isEmpty()) {
			// 유저 20개
			List<String> userss = userrepo.findUserAll();
			success.put("추천", "실패");
			result.add(success);
			for (String use : userss) {
				HashMap<String, Object> map = new HashMap<>();
				Users user = userrepo.findById(use).orElse(null);
				PetsitterProfile sitter = petsitterrepo.findById(use).orElse(null);
				HashMap<String, Object> schopmap = new HashMap<String, Object>();

				if (sitter != null) {

					// 프론트에서 필터링하는데 필요한 부분
					List<Dolbom> dol = dolbomrepo.findByUser2(user);

					for (Dolbom d : dol) {
						schopmap.put(d.getScheduleDay(), d.getDolbomOption());

					}
					map.put("userName", user.getUserName());
					map.put("userAddress", user.getUserAddress());
					map.put("sitterHouse", sitter.getSitterHouse());
					map.put("sitterMsg", sitter.getSitterMsg());
					map.put("sitterTem", sitter.getSitterTem());
					map.put("scheduleDay", schopmap);
					result.add(map);
				}
			}
			return result;
		} else {
			success.put("추천", "성공");
			result.add(success);
			for (String fil : filter4) {
				// 필요한 정보들만 추출
				HashMap<String, Object> map = new HashMap<>();
				Users user = userrepo.findById(fil).orElse(null);
				PetsitterProfile sitter = petsitterrepo.findById(fil).orElse(null);
				HashMap<String, Object> schopmap = new HashMap<String, Object>();

				if (sitter != null) {
					// 프론트에서 필터링하는데 필요한 부분
					List<Dolbom> dol = dolbomrepo.findByUser2(user);

					for (Dolbom d : dol) {
						schopmap.put(d.getScheduleDay(), d.getDolbomOption());
					}
					map.put("userName", user.getUserName());
					map.put("userAddress", user.getUserAddress());
					map.put("sitterHouse", sitter.getSitterHouse());
					map.put("sitterMsg", sitter.getSitterMsg());
					map.put("sitterTem", sitter.getSitterTem());
					map.put("scheduleDay", schopmap);
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
		map.put("reviewScore", allReview);
		map.put("review", reviews);
		map.put("sitterSex",user.getUserSex());
		map.put("sitterAge", user.getUserAge());
		map.put("sitterHousetype", sitter.getSitterHousetype());
		
		dolList.add(map);
		
		HashMap<String, Object> map2 = new HashMap<String, Object>();
	
		

		return dolList;
	}

	// 돌봄예약
	public int dolbomReservation(String userId, String sitterId, String scheduleDay, String[] scheduleHour,
			String petName) {
		int msg = 0;
		Users user = userrepo.findById(userId).orElse(null);
		Users sitter = userrepo.findById(sitterId).orElse(null);// 펫시터 가져오기
		PetProfile pet = petrepo.findByUserAndPetName(user, petName);

		for (String s : scheduleHour) {// 스케쥴 시간 포문
			// 돌봄테이블에서 펫시터의 날짜 시간 일치하는거 가져와서 예약되었다고 표시.
			Dolbom dol = dolbomrepo.findByUser2AndScheduleDayAndScheduleHour(sitter, scheduleDay, s);
			dol.setUser1(user);
			dol.setDolbomStatus(2); // 0->안된거 1-> 된거 2->대기
			dol.setPetProfile(pet);
			dolbomrepo.save(dol);
			msg = 1;
		}
		Alarm al = Alarm.builder().user(sitter).alarmMsg(user.getUserId() + " 님이 돌봄 요청서를 보내셨습니다:").alarmState(false)
				.build();
		alrepo.save(al);

		System.out.println(al.getAlarmMsg());
		return msg;
	}

	// 돌봄 확인 체크
	public List<Object> dolbomCheckPetsitter(String userId) {
		Users user = userrepo.findById(userId).orElse(null);
		List<Object> result = new ArrayList<Object>();

		List<Dolbom> dolbomuser = dolbomrepo.findByUser1(user);// 내가 유저인 것
		List<Dolbom> dolbomsitter = dolbomrepo.findByUser2Desc(userId);// 내가 펫시터인 것

		int lastday = 0;
		int lasthour = 0;

		HashMap<String, Object> bolmap = new HashMap<String, Object>();
		// 날짜가 이 전 날짜랑 비교해서 같거나 하나 작을때 근데 만약 30~1일 예약이면은..?
		// 시간이 이 전 시간이랑 비교해서 하나 작은숫자이거나 day가 줄었는데 hour가 24일때
		// 상대방 사진,상대방 이름, 돌봄 상태, 기간
		for (int i = 0; i < dolbomsitter.size(); i++) {
			String day = dolbomsitter.get(i).getScheduleDay();
			int daynum = Integer.parseInt(day.substring(day.length() - 2, day.length()));
			String hour = dolbomsitter.get(i).getScheduleHour();
			String hours[] = dolbomsitter.get(i).getScheduleHour().split(":");
			int hournum = Integer.parseInt(hours[0]);
			
			//상대방 사진
			Users sangdae =	userrepo.findById(dolbomsitter.get(i).getUser1().getUserId()).orElse(null);
			String sangdaeImg = sangdae.getUserImg();
			String sangdaeName = sangdae.getUserName();

			if (lastday == 0 && lasthour == 0) {//시작
				lastday = daynum;
				lasthour = hournum;
				
				bolmap.put("EndDay", day);
				bolmap.put("EndHour", (hournum+1)+":00");
			} else if (i == dolbomsitter.size() - 1) {// 마지막 인덱스일 때
				
				if((daynum == lastday && hournum == lasthour - 1) || (daynum == lastday - 1 && hournum == 24)) {//마지막 인덱스인데 시간 연속
					HashMap<String, Object> bolmap2 = new HashMap<String, Object>();
					bolmap.put("StartDay", day);
					bolmap.put("StartHour", hour);
					bolmap.put("sangdaeImg", sangdaeImg);
					bolmap.put("sangdaeName", sangdaeName);
					bolmap2.put("map", bolmap.clone());
					
					result.add(bolmap2);
					
					lastday = 0;
					lasthour = 0;
				}else {//마지막 인덱스인데 시간 끊김
					HashMap<String, Object> bolmap2 = new HashMap<String, Object>();
					bolmap.put("sangdaeImg", sangdaeImg);
					bolmap.put("sangdaeName", sangdaeName);
					bolmap2.put("map", bolmap.clone());
					
					result.add(bolmap2);
					
					HashMap<String, Object> bolmap3 = new HashMap<String, Object>();
					bolmap.put("EndDay", day);
					bolmap.put("EndHour", (hournum+1)+":00");
					bolmap.put("StartDay", day);
					bolmap.put("StartHour", hour);
					bolmap.put("sangdaeImg", sangdaeImg);
					bolmap.put("sangdaeName", sangdaeName);
					bolmap3.put("map", bolmap.clone());
					
					result.add(bolmap3);
				}
			} else if ((daynum == lastday && hournum == lasthour - 1) || (daynum == lastday - 1 && hournum == 24)) {//시간 연속
				bolmap.put("StartDay", day);
				bolmap.put("StartHour", hour);
				
				lastday = daynum;
				lasthour = hournum;
			} else {//시간 끊기면
				HashMap<String, Object> bolmap2 = new HashMap<String, Object>();
				bolmap.put("sangdaeImg", sangdaeImg);
				bolmap.put("sangdaeName", sangdaeName);
				bolmap2.put("map", bolmap.clone());
				
				result.add(bolmap2);
				
				lastday = daynum;
				lasthour = hournum;
				
				bolmap.put("EndDay", day);
				bolmap.put("EndHour", (hournum+1)+":00");
				bolmap.put("StartDay", day);
				bolmap.put("StartHour", hour);
			}
		}

		return result;
	}

}
