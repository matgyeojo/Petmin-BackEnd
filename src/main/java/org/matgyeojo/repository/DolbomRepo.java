package org.matgyeojo.repository;

import java.util.List;

import org.matgyeojo.dto.Dolbom;
import org.matgyeojo.dto.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface DolbomRepo extends CrudRepository<Dolbom, Integer>{


	
	//사용자 누군지
	public List<Dolbom> findByUser1(Users user);
	//사용자 누군지 내림차순
	@Query(value = "select * \r\n"
			+ "from dolbom where user_id = ?1 and dolbom_status in(1,2) order by start_day desc ", nativeQuery = true)
	public List<Dolbom> findByUser1Desc(String userId);
	//펫시터가 누군지
	public List<Dolbom> findByUser2(Users user);
	//펫시터가 누군지 내림차순 
	@Query(value = "select * \r\n"
			+ "from dolbom where petsitter_id = ?1 and dolbom_status in(1,2) order by schedule_day desc , schedule_hour desc", nativeQuery = true)
	public List<Dolbom> findByUser2Desc(String userId);
	
}
 