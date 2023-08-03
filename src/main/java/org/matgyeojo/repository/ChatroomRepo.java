package org.matgyeojo.repository;

import org.matgyeojo.dto.Chatroom;
import org.matgyeojo.dto.Users;
import org.springframework.data.repository.CrudRepository;



public interface ChatroomRepo extends CrudRepository<Chatroom, Integer>{

	 Chatroom findBySenderAndReceiver(Users sender, Users receiver);

}

