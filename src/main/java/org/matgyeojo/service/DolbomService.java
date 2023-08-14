package org.matgyeojo.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpStatus;
import org.matgyeojo.dto.Alarm;
import org.matgyeojo.dto.Chatroom;
import org.matgyeojo.dto.Dolbom;
import org.matgyeojo.dto.PetProfile;
import org.matgyeojo.dto.PetsitterProfile;
import org.matgyeojo.dto.Preference;
import org.matgyeojo.dto.Review;
import org.matgyeojo.dto.Schedule;
import org.matgyeojo.dto.Users;
import org.matgyeojo.repository.AlarmRepo;
import org.matgyeojo.repository.ChatroomRepo;
import org.matgyeojo.repository.DolbomRepo;
import org.matgyeojo.repository.PetProfileRepo;
import org.matgyeojo.repository.PetsitterProfileRepo;
import org.matgyeojo.repository.PreferenceRepo;
import org.matgyeojo.repository.ReviewRepo;
import org.matgyeojo.repository.ScheduleRepo;
import org.matgyeojo.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
	@Autowired
	ScheduleRepo schrepo;
	@Autowired
	ChatroomRepo chatroomRepo;

	// 돌봄 선호필터
	public List<Object> dolbomFilter(String userId, String userAddress) {
		System.out.println(userAddress);
		Users u = userrepo.findById(userId).orElse(null);// 유저 가져오기
		Preference pre = prerepo.findByUser(u);// 유저의 선호필터 가져오기
		// String userSex,int userAge,String sitterHousetype, String petSex,double
		// petWeight,String userAddress
		// 선호도 저장
		String userSex = pre.getPreference1();
		;
		int userAge = pre.getPreference2();
		String sitterHousetype = pre.getPreference3();
		String petSex = pre.getPreference4();
		String petWeight = pre.getPreference5();
		if (userAddress.equals("")) {// 입력값에 아무것도 입력 안하면 기본으로 자신의 주소
			userAddress = u.getUserAddress();
		} else {
			int openParenthesisIndex = userAddress.lastIndexOf("(");
			if (openParenthesisIndex == -1) {
				System.out.println(userAddress);
			} else {
				int commaIndex = userAddress.lastIndexOf(",");
				if (commaIndex == -1) {
					commaIndex = userAddress.lastIndexOf(")");
					String extractedDong = userAddress.substring(openParenthesisIndex + 1, commaIndex);
					extractedDong = extractedDong.trim();
					userAddress = extractedDong;
					System.out.println(userAddress);
				} else {
					commaIndex = userAddress.lastIndexOf(",");
					String extractedDong = userAddress.substring(openParenthesisIndex + 1, commaIndex);
					extractedDong = extractedDong.trim();
					userAddress = extractedDong;
					System.out.println(userAddress);
				}

			}

		}

		List<String> filter4 = new ArrayList<>();
		System.out.println(userAddress);
		if (petWeight.equals("소형견")) {
			int petweight = 10;
			filter4 = petrepo.findso(userAge, userSex, userAddress, sitterHousetype);
		} else if (petWeight.equals("중형견")) {
			int petweight = 10;
			filter4 = petrepo.findso(userAge, userSex, userAddress, sitterHousetype);
		} else {
			int petweight = 10;
			filter4 = petrepo.findso(userAge, userSex, userAddress, sitterHousetype);
		}

		// 1.users 테이블 = 성별, 나이
		// 2.sitter테이블 = 집 타입
		// 3.펫 프로필 테이블 = 성별 , 몸무게 (소형견 : 10kg미만,중형견:10~25,대형견 25~)
		// 성별,나이,사는곳

		// List에 map을 저장
		List<Object> result = new ArrayList<>();

		HashMap<String, String> success = new HashMap<String, String>();
		System.out.println(filter4);
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
					List<Schedule> sche = schrepo.findByUser(user);

					for (Schedule sc : sche) {
						schopmap.put(sc.getScheduleDay(), sc.getDolbomOption());

					}
					map.put("userName", user.getUserName());
					map.put("userAddress", user.getUserAddress());
					map.put("sitterHouse", sitter.getSitterHouse());
					map.put("sitterMsg", sitter.getSitterMsg());
					map.put("sitterTem", sitter.getSitterTem());
					map.put("scheduleDay", schopmap);
					map.put("userId", user.getUserId());
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
					List<Schedule> sche = schrepo.findByUser(user);

					for (Schedule sc : sche) {
						schopmap.put(sc.getScheduleDay(), sc.getDolbomOption());
					}
					map.put("userName", user.getUserName());
					map.put("userAddress", user.getUserAddress());
					map.put("sitterHouse", sitter.getSitterHouse());
					map.put("sitterMsg", sitter.getSitterMsg());
					map.put("sitterTem", sitter.getSitterTem());
					map.put("scheduleDay", schopmap);
					map.put("userId", user.getUserId());
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
		List<Schedule> sche = schrepo.findByUser(user);

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
		map.put("sitterSex", user.getUserSex());
		map.put("sitterAge", user.getUserAge());
		map.put("sitterHousetype", sitter.getSitterHousetype());

		dolList.add(map);

		HashMap<String, Object> map2 = new HashMap<String, Object>();

		return dolList;
	}

//	// 돌봄예약
//	public int dolbomReservation(String userId, String sitterId, String[] scheduleDay, String[] scheduleHour,
//			String petName) {
//		int msg = 0;
//		Users user = userrepo.findById(userId).orElse(null);
//		Users sitter = userrepo.findById(sitterId).orElse(null);// 펫시터 가져오기
//		PetProfile pet = petrepo.findByUserAndPetName(user, petName);
//		String startDay = scheduleDay[0] + " " + scheduleHour[0];
//		String h[] = scheduleHour[scheduleHour.length - 1].split(":");
//		String endDay = scheduleDay[scheduleDay.length - 1] + " " + (Integer.parseInt(h[0]) + 1) + ":00";
//		String option = null;
//		String scheNo = "";
//		int check = dolbomrepo.findByUserSitterStart(userId, sitterId, startDay);
//		if (check > 0) {
//			return msg;
//		}
//		for (String s : scheduleHour) {// 스케쥴 시간 포문
//			for (String ss : scheduleDay) {
//				// 돌봄테이블에서 펫시터의 날짜 시간 일치하는거 가져와서 예약되었다고 표시.
//				Schedule sche = schrepo.findByUserAndScheduleDayAndScheduleHour(sitter, ss, s);
//				sche.setDolbomStatus(2); // 0->안된거 1-> 된거 2->대기
//				schrepo.save(sche);
//				msg = 1;
//				option = sche.getDolbomOption();
//				scheNo += (sche.getScheduleNo() + ",");
//			}
//		}
//
//		scheNo = scheNo.substring(0, scheNo.length() - 1);
//		Dolbom dol = Dolbom.builder().user1(user).user2(sitter).START_CARE(startDay).END_CARE(endDay)
//				.dolbomStatus("대기중").dolbomOption(option).petProfile(pet).scheduleNo(scheNo).build();
//		dolbomrepo.save(dol);
//
//		Alarm al = Alarm.builder().user(sitter).alarmMsg(user.getUserId() + " 님이 돌봄 요청서를 보내셨습니다:").alarmState(false)
//				.build();
//		alrepo.save(al);
//
//		return msg;
//	}

	// 돌봄예약
	public int dolbomReservation(String userId, String sitterId, String[] scheduleDay, String petName) {
		int msg = 0;
		Users user = userrepo.findById(userId).orElse(null);
		Users sitter = userrepo.findById(sitterId).orElse(null);// 펫시터 가져오기
		PetProfile pet = petrepo.findByUserAndPetName(user, petName);
		
		String[] startsche = scheduleDay[0].split(" ");// 2023-04-04 14:00 형태 쪼개기
		String[] endsche = scheduleDay[scheduleDay.length-1].split(" ");// 2023-04-04 14:00 형태 쪼개기
		String startDay = startsche[0] + " " + startsche[1];
		String h[] = endsche[1].split(":");
		String endDay = endsche[0] + " " + (Integer.parseInt(h[0]) + 1) + ":00";
		
		String option = null;
		String scheNo = "";//스케쥴 넘버 저장할거
		for (String scheday : scheduleDay) {
		
			int check = dolbomrepo.findByUserSitterStart(userId, sitterId, startDay);
			if (check > 0) {
				return msg;
			}
			
			String[] day = scheday.split(" ");
			// 돌봄테이블에서 펫시터의 날짜 시간 일치하는거 가져와서 예약되었다고 표시.
			Schedule sche = schrepo.findByUserAndScheduleDayAndScheduleHour(sitter, day[0], day[1]);
			sche.setDolbomStatus(2); // 0->안된거 1-> 된거 2->대기
			schrepo.save(sche);
			msg = 1;
			option = sche.getDolbomOption();
			scheNo += (sche.getScheduleNo() + ",");
		}

		scheNo = scheNo.substring(0, scheNo.length() - 1);
		Dolbom dol = Dolbom.builder().user1(user).user2(sitter).START_CARE(startDay).END_CARE(endDay)
				.dolbomStatus("대기중").dolbomOption(option).petProfile(pet).scheduleNo(scheNo).build();
		dolbomrepo.save(dol);

		Alarm al = Alarm.builder().user(sitter).alarmMsg(user.getUserId() + " 님이 돌봄 요청서를 보내셨습니다:").alarmState(false)
				.build();
		alrepo.save(al);

		return msg;
	}

	// 돌봄 확인 체크
	public List<Object> dolbomCheckPetsitter(String userId) throws ParseException {
		List<Object> result = new ArrayList<Object>();
		Users user = userrepo.findById(userId).orElse(null);
		List<Dolbom> dolbomsitter = dolbomrepo.findByUser2Desc(userId);// 내가 펫시터인 것

		for (Dolbom dol : dolbomsitter) {
			HashMap<String, Object> bolmap = new HashMap<String, Object>();// 여기에 정보 담아서 result에 add
			PetProfile pet = petrepo.findById(dol.getPetProfile().getPetNo()).orElse(null);// 상대방의 펫
			Users sangdae = userrepo.findById(dol.getUser1().getUserId()).orElse(null);// 상대방 정보

			String petW = null;
			if (pet.getPetWeight() < 10) {
				petW = "소형견";
			} else if (pet.getPetWeight() > 25) {
				petW = "대형견";
			} else {
				petW = "중형견";
			}

			// 현재 시간이랑 비교해 돌봄 상태 변경
			Date now = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date sDate = sdf.parse(dol.getSTART_CARE());
			Date eDate = sdf.parse(dol.getEND_CARE());
			if ((eDate.getTime() - now.getTime()) < 0 && dol.getDolbomStatus().equals("진행중")) {
				dol.setDolbomStatus("종료");
				dolbomrepo.save(dol);
			} else if (dol.getDolbomStatus().equals("수락완료") && (eDate.getTime() - now.getTime()) > 0
					&& (sDate.getTime() - now.getTime()) < 0) {
				dol.setDolbomStatus("진행중");
				dolbomrepo.save(dol);
			} else if (dol.getDolbomStatus().equals("대기중") && (eDate.getTime() - now.getTime()) < 0) {
				dol.setDolbomStatus("기간마감");
				dolbomrepo.save(dol);
			}

			bolmap.put("no", dol.getDolbomNo());
			System.out.println(dol.getDolbomNo());
			bolmap.put("startday", dol.getSTART_CARE());
			bolmap.put("endday", dol.getEND_CARE());
			bolmap.put("sangdaeName", sangdae.getUserName());
			bolmap.put("sangdaeId", sangdae.getUserId());
			bolmap.put("state", dol.getDolbomStatus());
			bolmap.put("pet", pet);
			bolmap.put("petW", petW);
			bolmap.put("option", dol.getDolbomOption());

			result.add(bolmap);
		}

		return result;
	}

	// 대기중 -> 수락완료
	public String dolbomsurack(int dolbomNo) {
		String msg = "실패";
		Dolbom dolbom = dolbomrepo.findById(dolbomNo).orElse(null);
		if (dolbom != null) {
			msg = "성공";
		}
		String ds = dolbom.getScheduleNo();
		System.out.println(ds.indexOf(","));
		if (ds.indexOf(",") > 0) {
			String[] scheNos = dolbom.getScheduleNo().split(",");

			for (String sc : scheNos) {
				int scheNo = Integer.parseInt(sc);
				Schedule schedule = schrepo.findById(scheNo).orElse(null);
				schedule.setDolbomStatus(1);
				schrepo.save(schedule);

			}
		} else {
			int scheNo = Integer.parseInt(ds);
			Schedule schedule = schrepo.findById(scheNo).orElse(null);
			schedule.setDolbomStatus(1);
			schrepo.save(schedule);

		}

		dolbom.setDolbomStatus("수락완료");
		dolbomrepo.save(dolbom);
		// 사용자를 찾아서 알람전송
		Users user = userrepo.findById(dolbom.getUser1().getUserId()).orElse(null);
		Users sitter = userrepo.findById(dolbom.getUser2().getUserId()).orElse(null);
		Alarm al = Alarm.builder().user(user).alarmMsg(sitter.getUserId() + " 님이 돌봄 요청을 수락하셨습니다:").alarmState(false)
				.build();
		alrepo.save(al);

		return msg;
	}

	// 유저일때 예약확인 페이지 정보뿌려주기
	public List<Object> dolbomCheckuser(String userId) throws ParseException {
		List<Object> result = new ArrayList<>();

		List<Dolbom> dolbomuser = dolbomrepo.findByUser1Desc(userId);// 내가 유저인것

		for (Dolbom dol : dolbomuser) {
			HashMap<String, Object> bolmap = new HashMap<String, Object>();// 여기에 정보 담아서 result에 add
			PetProfile pet = petrepo.findById(dol.getPetProfile().getPetNo()).orElse(null);// 내강아지 펫
			Users sangdae = userrepo.findById(dol.getUser2().getUserId()).orElse(null);// 상대방 정보

			String petW = null;
			if (pet.getPetWeight() < 10) {
				petW = "소형견";
			} else if (pet.getPetWeight() > 25) {
				petW = "대형견";
			} else {
				petW = "중형견";
			}

			// 현재 시간이랑 비교해 돌봄 상태 변경
			Date now = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date sDate = sdf.parse(dol.getSTART_CARE());
			Date eDate = sdf.parse(dol.getEND_CARE());
			if ((eDate.getTime() - now.getTime()) < 0 && dol.getDolbomStatus().equals("진행중")) {
				dol.setDolbomStatus("종료:리뷰 작성 전");
				dolbomrepo.save(dol);
			} else if (dol.getDolbomStatus().equals("수락완료") && (eDate.getTime() - now.getTime()) > 0
					&& (sDate.getTime() - now.getTime()) < 0) {
				dol.setDolbomStatus("진행중");
				dolbomrepo.save(dol);
			} else if (dol.getDolbomStatus().equals("대기중") && (eDate.getTime() - now.getTime()) < 0) {
				dol.setDolbomStatus("기간마감");
				dolbomrepo.save(dol);
			}

			bolmap.put("no", dol.getDolbomNo());
			bolmap.put("startday", dol.getSTART_CARE());
			bolmap.put("endday", dol.getEND_CARE());
			bolmap.put("sangdaeName", sangdae.getUserName());
			bolmap.put("sangdaeId", sangdae.getUserId());
			bolmap.put("state", dol.getDolbomStatus());
			bolmap.put("pet", pet);
			bolmap.put("petW", petW);
			bolmap.put("option", dol.getDolbomOption());

			result.add(bolmap);
		}

		return result;
	}

	// 돌봄삭제
	public String dolbomDelete(int dolbomNo) {
		String msg = "실패";
		Dolbom dolbom = dolbomrepo.findById(dolbomNo).orElse(null);
		dolbomrepo.delete(dolbom);

		Users id1 = dolbom.getUser1();
		Users id2 = dolbom.getUser2();
		Chatroom room = chatroomRepo.findBySenderAndReceiver(id1, id2);
		chatroomRepo.delete(room);

		Dolbom dolbom2 = dolbomrepo.findById(dolbomNo).orElse(null);
		if (dolbom2 == null) {
			msg = "삭제완료";
		}
		return msg;
	}

	// 리뷰 리스트 출력
	public List<Object> reviewList(String sitterId) {
		Users user = userrepo.findById(sitterId).orElse(null);
		List<Object> result = new ArrayList<>();
		List<Review> reviews = reviewrepo.findByPetsitter(user);
		for (Review re : reviews) {
			Users reuser = userrepo.findById(re.getUser().getUserId()).orElse(null);
			String userName = reuser.getUserName();
			HashMap<String, Object> name = new HashMap<String, Object>();

			name.put("reviewNo", re.getReviewNo());
			name.put("reviewKind", re.getReviewKind());
			name.put("reviewDelecacy", re.getReviewDelecacy());
			name.put("reviewTime", re.getReviewTime());
			name.put("reviewMsg", re.getReviewMsg());
			name.put("userId", re.getUser().getUserId());
			name.put("petsitterId", re.getPetsitter().getUserId());
			name.put("userName", userName);
			result.add(name);
		}

		return result;
	}

	// 리뷰작성
	public String inReview(int dolbomNo, int reviewTime, int reviewKind, int reviewDelecacy, String reviewMsg) {
		String msg = "실패";
		Dolbom dolbom = dolbomrepo.findById(dolbomNo).orElse(null);
		Users user = userrepo.findById(dolbom.getUser1().getUserId()).orElse(null);// 사용자 아이디
		Users sitter = userrepo.findById(dolbom.getUser2().getUserId()).orElse(null);// 펫시터 아이디
		Review review = Review.builder().user(user).petsitter(sitter).reviewTime(reviewTime).reviewKind(reviewKind)
				.reviewDelecacy(reviewDelecacy).reviewMsg(reviewMsg).build();

		Review re = reviewrepo.save(review);
		if (re != null) {
			msg = "리뷰가 작성되었습니다";
			PetsitterProfile si = petsitterrepo.findById(sitter.getUserId()).get();
			double tem = si.getSitterTem();
			int sum = reviewTime + reviewKind + reviewDelecacy;
			double temp = (sum - 9) * 0.2;
			tem += temp;
			si.setSitterTem(tem);
			petsitterrepo.save(si);
			dolbom.setDolbomStatus("종료");
			dolbomrepo.save(dolbom);
		}

		return msg;
	}

}
