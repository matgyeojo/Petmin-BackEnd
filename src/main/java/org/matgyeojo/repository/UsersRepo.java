package org.matgyeojo.repository;

import org.matgyeojo.dto.Users;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepo extends CrudRepository<Users, String> {

	// Users findByUs(String userId);

	boolean existsByUserId(String userId);
}
