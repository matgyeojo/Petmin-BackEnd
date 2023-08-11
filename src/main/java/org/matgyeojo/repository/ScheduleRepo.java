package org.matgyeojo.repository;

import java.util.List;

import org.matgyeojo.dto.Dolbom;
import org.matgyeojo.dto.Schedule;
import org.matgyeojo.dto.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ScheduleRepo extends CrudRepository<Schedule, Integer>{
		//펫시터 가능한 날짜 필터링
		public List<Schedule> findByScheduleDay(String scheduleDay);
		//펫시터 가능한 날짜 중복체크 
		@Query(value = "select count(*) from schedule d where d.petsitter_id = ?1 and d.schedule_day = ?2 and d.schedule_hour = ?3", nativeQuery = true)
		public int findByUserAndScheduleDayAndScheduleHour(String SitterId,String scheduleDay,String scheduleHour);
		//사용자 누군지
		public List<Schedule> findByUser(Users user);
	
		//펫시터 돌봄옵션 필터링
		public List<Schedule> findByDolbomOptionLike(String dolbomOption);
		//펫시터 어떤 날
		public List<Schedule> findByUserAndScheduleDay(Users user,String scheduleDay);
		//펫시터의 어떤 날 어떤 시간
		public Schedule findByUserAndScheduleDayAndScheduleHour(Users user,String scheduleDay,String scheduleHour);
}
