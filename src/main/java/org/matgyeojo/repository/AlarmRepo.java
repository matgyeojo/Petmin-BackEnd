package org.matgyeojo.repository;

import java.util.List;

import org.matgyeojo.dto.Alarm;
import org.matgyeojo.dto.Users;
import org.springframework.data.repository.CrudRepository;

public interface AlarmRepo extends CrudRepository<Alarm, Integer>{
	//유저의 알람 찾기
	public List<Alarm> findByUser(Users user);
	
}
