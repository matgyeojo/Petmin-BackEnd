package org.matgyeojo.repository;

import java.util.Optional;

import org.matgyeojo.dto.Chatroom;
import org.matgyeojo.dto.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ChatroomRepo extends CrudRepository<Chatroom, Integer>{
	
}

