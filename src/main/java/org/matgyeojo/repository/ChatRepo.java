package org.matgyeojo.repository;

import java.util.List;

import org.matgyeojo.dto.Chat;
import org.matgyeojo.dto.Chatroom;
import org.springframework.data.repository.CrudRepository;

public interface ChatRepo extends CrudRepository<Chat, Integer>{
	List<Chat> findBychatroomOrderBychatDate (Chatroom room);
}
