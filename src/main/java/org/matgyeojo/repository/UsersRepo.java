package org.matgyeojo.repository;

import org.matgyeojo.dto.Users;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepo extends CrudRepository<Users, String>{
<<<<<<< HEAD
<<<<<<< HEAD
	boolean existsByUserId(String userId);
=======

>>>>>>> a73e42dcf52385969a2eff9f30cbe0251d853ceb
=======
	boolean existsByUserId(String userId);
>>>>>>> a40d83f5b42e493cd2667f0575d716fda69a7d2e
}
