package org.matgyeojo.repository;

import org.matgyeojo.dto.Users;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepo extends CrudRepository<Users, String>{
<<<<<<< HEAD
	boolean existsByUserId(String userId);
=======

>>>>>>> a73e42dcf52385969a2eff9f30cbe0251d853ceb
}
