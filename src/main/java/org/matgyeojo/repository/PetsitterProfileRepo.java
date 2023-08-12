package org.matgyeojo.repository;

import java.util.List;

import org.matgyeojo.dto.PetsitterProfile;
import org.matgyeojo.dto.Users;
import org.springframework.data.repository.CrudRepository;

public interface PetsitterProfileRepo extends CrudRepository<PetsitterProfile, String>{
	
	public PetsitterProfile findByUsers(Users user);
	//펫시터 집 타입 필터링
	public List<PetsitterProfile> findBySitterHousetypeOrderBySitterUpdateDesc(String sitterHousetype);
	
}
