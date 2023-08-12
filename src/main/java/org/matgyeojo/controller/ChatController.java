package org.matgyeojo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.matgyeojo.dto.Chat;
import org.matgyeojo.dto.Chatroom;
import org.matgyeojo.dto.Users;
import org.matgyeojo.repository.ChatRepo;
import org.matgyeojo.repository.ChatroomRepo;
import org.matgyeojo.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatController {

   @Autowired
   ChatRepo chatRepo;

   @Autowired
   ChatroomRepo chatroomRepo;

   @Autowired
   UsersRepo usersRepo;

//   @GetMapping(value = { "/consult/chartoom" })
//   public <T> T adminEnterChatRoom(HttpServletRequest request) {
//      System.out.println("채팅 입장 요청 들어옴");
//      String chatroomid = request.getParameter("chatroomid");
//      Chatroom chatroom = chatroomRepo.findById(Long.parseLong(chatroomid)).get();
//      List<Chat> chatlist = chatRepo.findByChatroomOrderByChatDate(chatroom);
//      return (T) chatlist;
//   }

   // 회원이 프로필 보고 채팅요청을 통해 채팅을 처음 들어오는 부분
   @GetMapping("/chatting")
   public <T> T createOrEnterChatRoom(HttpServletRequest request, @RequestParam("sender") String sender,
         @RequestParam("receiver") String receiver) {
      Map<String, Object> map = new HashMap<>();

      Users senderUser = usersRepo.findById(sender).orElse(null);
      Users receiverUser = usersRepo.findById(receiver).orElse(null);
      if (senderUser != null && receiverUser != null) {
         // 보내는 사람과 받는 사람 간에 이미 생성된 채팅방 조회
         Chatroom existingRoom = chatroomRepo.findBySenderAndReceiver(senderUser, receiverUser);

         if (existingRoom == null) {
            // 채팅방이 없으면 새로 생성
            Chatroom newRoom = new Chatroom();
            newRoom.setSender(senderUser);
            newRoom.setReceiver(receiverUser);
            newRoom = chatroomRepo.save(newRoom);

            map.put("message", "채팅을 연결합니다.");
            map.put("chatroomId", newRoom.getChatroomId());
            map.put("sender", senderUser);
            map.put("receiver", receiverUser);
         } else {
            int chatId = existingRoom.getChatroomId();
            map.put("message", "상담 신청 내역이 있습니다. 이전 채팅방에 입장합니다.");
            map.put("chatroomId", existingRoom.getChatroomId());
            List<Chat> chatHistory = chatRepo.findByChatroomOrderByChatDate(existingRoom);
            map.put("chatHistory", chatHistory);
         }
      } else {
         map.put("message", "사용자 정보를 찾을 수 없습니다.");
      }

      return (T) map;
   }

   // 채팅 리스트 및 유저 정보전달
   @GetMapping("/chatlist")
   public List<Chatroom> getChatroomByReceiver(@RequestParam("userId") String userId) {
      // userId로 사용자 정보 가져오기
      Users users = usersRepo.findById(userId).orElse(null);

      if (users == null) {
         // 사용자가 존재하지 않으면 예외 처리 또는 적절한 응답을 보내줄 수 있습니다.
         return null;
      }

      // userId를 기준으로 해당 사용자가 참여하는 채팅방 정보 가져오기
      List<Chatroom> chatroom = chatroomRepo.findByUserId(userId);

      return chatroom;
   }
   
   //채팅방 찾기 유저정보랑 펫시터 정보가 주어짐
   @GetMapping(value = "/findroom")
   public int findRoom(@RequestParam String Id1,@RequestParam String Id2) {
	   int room = chatroomRepo.findRoom(Id1, Id2);
	   if(room>0) {
		   
	   }else {
		     Users senderUser = usersRepo.findById(Id1).orElse(null);
		      Users receiverUser = usersRepo.findById(Id2).orElse(null);
		// 채팅방이 없으면 새로 생성
           Chatroom newRoom = new Chatroom();
           newRoom.setSender(senderUser);
           newRoom.setReceiver(receiverUser);
           newRoom = chatroomRepo.save(newRoom);
           room = newRoom.getChatroomId();
	   }http://test1@1234
	   return room;
   }
}