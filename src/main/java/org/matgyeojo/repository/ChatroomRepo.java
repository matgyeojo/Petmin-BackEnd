package org.matgyeojo.repository;

import java.util.List;

import org.matgyeojo.dto.Chatroom;
import org.matgyeojo.dto.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ChatroomRepo extends CrudRepository<Chatroom, Long> {

   @Query(value = "select * from chatroom r  where (r.receiver_id = ?1 and  r.sender_id = ?2) or ( r.receiver_id = ?2 and  r.sender_id = ?1)", nativeQuery = true)
   Chatroom findBySenderAndReceiver(Users sender, Users receiver);

   @Query(value = "SELECT * FROM chatroom r WHERE (r.receiver_id = :userId OR r.sender_id = :userId)", nativeQuery = true)
   List<Chatroom> findByUserId(@Param("userId") String userId);

}