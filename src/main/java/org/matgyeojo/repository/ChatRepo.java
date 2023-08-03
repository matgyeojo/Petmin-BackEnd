package org.matgyeojo.repository;

import java.util.List;

import org.matgyeojo.dto.Chat;
import org.matgyeojo.dto.Chatroom;
import org.springframework.data.repository.CrudRepository;

public interface ChatRepo extends CrudRepository<Chat, Integer>{
    
    // 채팅방 번호를 기준으로 채팅 내역을 가져오고 chatDate로 정렬합니다.
    List<Chat> findByChatroomOrderByChatDate(Chatroom chatroom);

}
