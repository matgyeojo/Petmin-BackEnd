package org.matgyeojo.repository;

import java.util.List;

import org.matgyeojo.dto.PetProfile;
import org.matgyeojo.dto.Users;
import org.springframework.data.repository.CrudRepository;

public interface PetProfileRepo extends CrudRepository<PetProfile, Integer>{
	
	//펫 이름 검색
	public PetProfile findByPetName(String petName);
	//펫 주인 검색
//	public List<PetProfile> findByUsers(Users user);
}
