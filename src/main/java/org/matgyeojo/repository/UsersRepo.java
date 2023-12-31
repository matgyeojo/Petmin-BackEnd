package org.matgyeojo.repository;

import org.matgyeojo.dto.Users;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepo extends CrudRepository<Users, String>{

	boolean existsByUserId(String userId);

}
