package org.matgyeojo.repository;

import java.util.List;

import org.matgyeojo.dto.Dolbom;
import org.matgyeojo.dto.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface DolbomRepo extends CrudRepository<Dolbom, Integer>{

	//펫시터 가능한 날짜 필터링
	public List<Dolbom> findByScheduleDay(String scheduleDay);
	//펫시터 가능한 날짜 중복체크 
	@Query(value = "select count(*) from dolbom d where d.petsitter_id = ?1 and d.schedule_day = ?2 and d.schedule_hour = ?3", nativeQuery = true)
	public int findByUser2AndScheduleDayAndScheduleHour(String SitterId,String scheduleDay,String scheduleHour);
	//사용자 누군지
	public List<Dolbom> findByUser1(Users user);
	//사용자 누군지 내림차순
	@Query(value = "select * \r\n"
			+ "from dolbom where user_id = ?1 and dolbom_status in(1,2) order by schedule_day desc , schedule_hour desc", nativeQuery = true)
	public List<Dolbom> findByUser1Desc(String userId);
	//펫시터가 누군지
	public List<Dolbom> findByUser2(Users user);
	//펫시터가 누군지 내림차순 
	@Query(value = "select * \r\n"
			+ "from dolbom where petsitter_id = ?1 and dolbom_status in(1,2) order by schedule_day desc , schedule_hour desc", nativeQuery = true)
	public List<Dolbom> findByUser2Desc(String userId);
	//펫시터 돌봄옵션 필터링
	public List<Dolbom> findByDolbomOptionLike(String dolbomOption);
	//펫시터 어떤 날
	public List<Dolbom> findByUser2AndScheduleDay(Users user,String scheduleDay);
	//펫시터의 어떤 날 어떤 시간
	public Dolbom findByUser2AndScheduleDayAndScheduleHour(Users user,String scheduleDay,String scheduleHour);
}
 