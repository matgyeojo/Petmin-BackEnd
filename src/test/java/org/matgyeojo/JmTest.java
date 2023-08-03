package org.matgyeojo;

import org.junit.jupiter.api.Test;
import org.matgyeojo.dto.Dolbom;
import org.matgyeojo.dto.PetProfile;
import org.matgyeojo.dto.PetsitterProfile;
import org.matgyeojo.dto.Users;
import org.matgyeojo.repository.DolbomRepo;
import org.matgyeojo.repository.PetProfileRepo;
import org.matgyeojo.repository.PetsitterProfileRepo;
import org.matgyeojo.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JmTest {

	@Autowired
	UsersRepo userrepo;
	@Autowired
	PetsitterProfileRepo petsitterrepo;
	@Autowired
	PetProfileRepo petrepo;
	@Autowired
	DolbomRepo dolbomrepo;
	
	//돌봄 등록
	@Test
	void dolbomInsert() {
		Users user = userrepo.findById("지만2").orElse(null);
		Users sitter =  userrepo.findById("지만").orElse(null);
		PetProfile pet = petrepo.findByPetName("자몽");
		
		Dolbom dol = Dolbom.builder().user1(user).user2(sitter).petProfile(pet).scheduleDay("2023/08/01").scheduleHour("00:00").dolbomStatus(false).dolbomOption("단기예약").build();
		
		dolbomrepo.save(dol);
		
	}
	
	//펫등록
	//@Test
	void petInsert() {
		Users user = userrepo.findById("지만").orElse(null);
		
		PetProfile pet = PetProfile.builder()
				.petName("자몽")
				//.petAge(6)
				//.petSpecies("포메라니안")
				//.petWeight(6.17)
				//.petSex("남")
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
		
		Users user = userrepo.findById("지만").get();
		PetsitterProfile sitter = PetsitterProfile.builder()
									.users(user)
									.sitterHousetype("아파트")
									.sitterMsg("저는 이런사람입니다")
									.sitterTem(39.0)
									.build();

		user.setPetsitterProfile(sitter);
		userrepo.save(user);//유저 밑에 시터가 있기때문에 유저를 저장
	}
	
	//유저입력
	//@Test
	void userInsert() {
		Users u1 = Users.builder().userId("지만").userPass("1234").userLicence("실버펫시터").build();
		userrepo.save(u1);
		Users u2 = Users.builder().userId("지만2").userPass("1234").userLicence("실버펫시터").build();
		userrepo.save(u2);
	}
}
