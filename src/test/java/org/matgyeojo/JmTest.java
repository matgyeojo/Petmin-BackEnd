package org.matgyeojo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
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
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.java.Log;

@SpringBootTest
@Log
public class JmTest {

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
	ReviewRepo rerepo;
	
	//리뷰입력
	@Test
	void reInsert() {
		Users user = userrepo.findById("지만").orElse(null);
		Users sitter = userrepo.findById("지만2").orElse(null);
		Review re = Review.builder()
				.user(user)
				.petsitter(sitter)
				.reviewTime(3)
				.reviewKind(4)
				.reviewDelecacy(1)
				.reviewMsg("아쉽네여~")
				.build();
		Review re2 = Review.builder()
				.user(user)
				.petsitter(sitter)
				.reviewTime(2)
				.reviewKind(5)
				.reviewDelecacy(3)
				.reviewMsg("아쉽네여~")
				.build();
		rerepo.save(re);
		rerepo.save(re2);
	}
	
	//선호 입력
	//@Test
	void preInsert() {
		Users user = userrepo.findById("지만").orElse(null);
		Preference p = Preference.builder().preference1("남").preference2(20).preference3("아파트").preference4("남아").preference5("소형견").build();
		prerepo.save(p);
	}
	
	//필터링
	//@Test
	void dolbomFilter() {
		//1.users 테이블 = 성별, 나이,주소
		//2.sitter테이블 = 집 타입
		//3.펫 프로필 테이블 = 성별 , 몸무게 (소형견 : 10kg미만,중형견:10~25,대형견 25~)
		List<Users> users = userrepo.findByUserSexAndUserAgeGreaterThanEqualAndUserAddressStartingWith("남", 20,"경기도 고양시");
		List<PetsitterProfile> sitters = petsitterrepo.findBySitterHousetypeOrderBySitterUpdateDesc("아파트");
		//if로 몸무게 조건걸어서 소중대형견 판별 지금은 예시로 소형견만
		List <PetProfile> pets = petrepo.findByPetSexAndPetWeightLessThan("남아", 10.00);
		
		List<String> filter1 = new ArrayList<String>();
		
		users.forEach(user->{
//			log.info(user.getUserId());
			filter1.add(user.getUserId());
		});
	
		List<String> filter2 = new ArrayList<String>();
		sitters.forEach(sitter->{
			filter2.add(sitter.getUserId());
//			for(String fil : filter) {
//				if(fil.equals(sitter.getUserId())) {
//					
//					count++;}
//				else {
//					filter.remove(count);
//					break;
//				}
//			}
//			
//			log.info(sitter.getUserId());
		});
		
		List<String> filter3 = new ArrayList<String>();
		pets.forEach(pet->{
			filter3.add(pet.getUser().getUserId());
//			for(String fil : filter) {
//				if(fil.equals(pet.getUser().getUserId())) {count++;}
//				else {
//					filter.remove(count);
//				}
//			}
//			log.info(pet.getUser().getUserId());
		});
		
		List<String> filter4= new ArrayList<String>();
		
		for(String fil1 : filter1) {
			for(String fil2 : filter2) {
				for(String fil3 : filter3) {
					if(fil1.equals(fil2) && fil2.equals(fil3) && fil3.equals(fil1)) {
						filter4.add(fil1);
					}
				}
			}
		}
//		//날짜 필터링
//		String scaduleDay = null;
//		if(scaduleDay != null) {
//			List<String> filter5= new ArrayList<String>();
//			List<Dolbom> dolboms = dolbomrepo.findByScheduleDay("2023/08/01");
//			dolboms.forEach(dol->{
//				for(String fil4:filter4) {
//					if(fil4.equals(dol.getUser2().getUserId())) {
//						filter5.add(dol.getUser2().getUserId());
//					}
//				}
//			});
//			for(String fil : filter5) {
//				log.info("필터링 된 값은 : "+fil);
//			}
//		}
		
		
		for(String fil : filter4) {
			log.info("필터링 된 값은 : "+fil);
		}
	}
	
	//선호필터 등록
	//@Test
	void preferenceInsert() {
		Users user = userrepo.findById("지만").get();
		Preference pre = Preference.builder().preference1("남").preference2(20).preference3("마당있는집").preference4("남아").preference5("소형견").user(user).build();

		prerepo.save(pre);
	
	}
	
	//돌봄 등록
	//@Test
	void dolbomInsert() {
		Users user = userrepo.findById("지만2").orElse(null);
		Users sitter =  userrepo.findById("지만").orElse(null);
		PetProfile pet = petrepo.findByPetName("자몽");
		
		Dolbom dol = Dolbom.builder().user2(sitter).scheduleDay("2023/08/01").scheduleHour("00:00").dolbomStatus(false).dolbomOption("단기예약").build();
		
		dolbomrepo.save(dol);
		
	}
	
	//펫등록
	//@Test
	void petInsert() {
		Users user = userrepo.findById("지만").orElse(null);
		
		PetProfile pet = PetProfile.builder()
				.petName("자몽")
				.petAge(6)
				.petSpecies("포메라니안")
				.petWeight(6.17)
				.petSex("남아")
				.user(user)
				.build();
		
		PetProfile pet2 = petrepo.save(pet);
//		pet2.setUser(user);
		
		petrepo.save(pet2);
			}
	
	//펫시터 수정
	//@Test
	void petsitterUpdate() {
		PetsitterProfile sitter = petsitterrepo.findById("지만").orElse(null);
		sitter.setSitterHousetype("단독주택");
		petsitterrepo.save(sitter);
	}
	
	//펫시터 저장
	//@Test
	void petsitterInsert() {
		
		Users user = userrepo.findById("지만").orElse(null);
		PetsitterProfile sitter = PetsitterProfile.builder().users(user).sitterTem(39.0).build();

		user.setPetsitterProfile(sitter);
//		petsitterrepo.save(sitter);
		userrepo.save(user);//유저 밑에 시터가 있기때문에 유저를 저장
	}
	
	//유저입력
	//@Test
	void userInsert() {
		Users u1 = Users.builder()
				.userId("지만")
				.userPass("1234")
				.userAge(25)
				.userAddress("경기도 일산동구")
				.userSex("남")
				.userName("지만")
				.userLicence("실버펫시터")
				.build();
		Users u11 = userrepo.save(u1);
		PetsitterProfile sitter1 = PetsitterProfile.builder()
				.users(u11)
				.sitterTem(39.00)
				.build();
		u11.setPetsitterProfile(sitter1);
		userrepo.save(u1);
		
		Users u2 = Users.builder()
				.userId("지만2")
				.userPass("1234")
				.userAge(25)
				.userAddress("경기도 일산동구")
				.userSex("남")
				.userName("지만2")
				.userLicence("실버펫시터")
				.build();
		Users u22 = userrepo.save(u2);
		PetsitterProfile sitter2 = PetsitterProfile.builder()
				.users(u22)
				.sitterTem(39.00)
				.build();
		u22.setPetsitterProfile(sitter2);
		userrepo.save(u2);
		
	}
}
