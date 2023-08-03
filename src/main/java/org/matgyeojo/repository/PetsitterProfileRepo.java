package org.matgyeojo.repository;

import org.matgyeojo.dto.PetsitterProfile;
import org.matgyeojo.dto.Users;
import org.springframework.data.repository.CrudRepository;

public interface PetsitterProfileRepo extends CrudRepository<PetsitterProfile, String>{
	
	public PetsitterProfile findByUsers(Users user);
}
