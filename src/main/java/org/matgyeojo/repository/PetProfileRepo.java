package org.matgyeojo.repository;

import java.util.List;

import org.matgyeojo.dto.PetProfile;
import org.matgyeojo.dto.Users;
import org.springframework.data.repository.CrudRepository;

public interface PetProfileRepo extends CrudRepository<PetProfile, Integer>{
	
	//펫 이름 검색
	public PetProfile findByPetName(String petName);
	//펫 주인 검색

	//펫 성별 , 몸무게 필터링(소형견)
	public List<PetProfile> findByPetSexAndPetWeightLessThan(String petSex,double petWeight);
	//펫 성별 , 몸무게 필터링(중형견)
	public List<PetProfile> findByPetSexAndPetWeightBetween(String petSex,double petWeight,double petWeight2);
	//펫 성별 , 몸무게 필터링(대형견)
	public List<PetProfile> findByPetSexAndPetWeightGreaterThan(String petSex,double petWeight);

	
	//해당 아이디의 반려견 프로필 정보 불러오기
	public List<PetProfile> findByUser(Users user);

}
  