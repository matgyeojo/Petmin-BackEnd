package org.matgyeojo.repository;

import java.util.Optional;

import org.matgyeojo.dto.Users;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepo extends CrudRepository<Users, String>{
	Optional<Users> findById(String userId);
}
