package org.matgyeojo.repository;

import org.matgyeojo.dto.Preference;
import org.matgyeojo.dto.Users;
import org.springframework.data.repository.CrudRepository;

public interface PreferenceRepo extends CrudRepository<Preference, Integer>{

	//유저로 검색
	public Preference findByUser(Users user);
}
