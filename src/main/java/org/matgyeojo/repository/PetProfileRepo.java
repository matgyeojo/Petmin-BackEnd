package org.matgyeojo.repository;

import java.util.List;

import org.matgyeojo.dto.PetProfile;
import org.matgyeojo.dto.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PetProfileRepo extends CrudRepository<PetProfile, Integer> {

	// 펫 이름 검색
	public PetProfile findByPetName(String petName);
	// 펫 주인 검색

//	// 펫 성별 , 몸무게 필터링(소형견)
//	public List<PetProfile> findByPetSexAndPetWeightLessThan(String petSex, double petWeight);
//
//	// 펫 성별 , 몸무게 필터링(중형견)
//	public List<PetProfile> findByPetSexAndPetWeightBetween(String petSex, double petWeight, double petWeight2);
//
//	// 펫 성별 , 몸무게 필터링(대형견)
//	public List<PetProfile> findByPetSexAndPetWeightGreaterThan(String petSex, double petWeight);

	// 소형견일때 필터링
	@Query(value = "select u.user_id\r\n"
			+ "from users u join petsitter_profile s on(u.user_id = s.user_id) \r\n"
			+ "join pet_profile p on (u.user_id=p.user_id)\r\n"
			+ "where u.user_age >= ?1 and u.user_sex = ?2 and u.user_address like %?3% "
			+ "and s.sitter_housetype = ?4 and p.pet_sex = ?5 and p.pet_weight <?6 limit 20", nativeQuery = true)
	public List<String> findso(int userAge,String userSex,String userAddress, String sitterHousetype, String petSex, int petWeight  );
	// 중형견일때 필터링
	@Query(value = "select u.user_id\r\n"
			+ "from users u join petsitter_profile s on(u.user_id = s.user_id) \r\n"
			+ "join pet_profile p on (u.user_id=p.user_id)\r\n"
			+ "where u.user_age >= ?1 and u.user_sex = ?2 and u.user_address like %?3% "
			+ "and s.sitter_housetype = ?4 and p.pet_sex = ?5 and p.pet_weight <?6 and p.pet_weight>=10 limit 20", nativeQuery = true)
	public List<String> findjoong(int userAge,String userSex,String userAddress, String sitterHousetype, String petSex, int petWeight  );
	// 대형견일때 필터링
	@Query(value = "select u.user_id\r\n"
			+ "from users u join petsitter_profile s on(u.user_id = s.user_id) \r\n"
			+ "join pet_profile p on (u.user_id=p.user_id)\r\n"
			+ "where u.user_age >= ?1 and u.user_sex = ?2 and u.user_address like %?3% "
			+ "and s.sitter_housetype = ?4 and p.pet_sex = ?5 and p.pet_weight >?6 limit 20", nativeQuery = true)
	public List<String> findsdae(int userAge,String userSex,String userAddress, String sitterHousetype, String petSex, int petWeight  );

	// 해당 아이디의 반려견 프로필 정보 불러오기
	public List<PetProfile> findByUser(Users user);

	//유저의 펫 한마리
	public PetProfile findByUserAndPetName(Users user,String petName);

}
