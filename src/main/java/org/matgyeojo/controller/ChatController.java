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
 
	// 과거 대화내용을 보내야한다.
	@GetMapping(value = { "/chatroom" })
	public <T> T PastChatRoom(HttpServletRequest request) {
		System.out.println("관리자 채팅 입장 요청 들어옴");

		String roomnumber = request.getParameter("roomnumber");
		Integer roomNumber = Integer.parseInt(roomnumber); // 문자열을 Integer로 변환
		
		Chatroom room = chatroomRepo.findById(roomNumber).orElse(null);
		
		List<Chat> chatlist = chatRepo.findByChatroomOrderByChatDate(room); //과거 채팅방이 있을경우 Date로 정렬
		
		return (T) chatlist; 
	}
	
	
	// 회원이 프로필 보고 채팅요청을 통해 채팅을 처음 들어오는 부분
	// 없으면 채팅룸 생성-> 있으면 룸번호 반환
	@GetMapping("/chatting")
	public <T> T createRoom(HttpServletRequest request, @RequestParam("senderId") String senderId, 
	                                              @RequestParam("receiverId") String receiverId) {
	    // 결과를 담을 Map 객체 생성
	    Map<String, Object> map = new HashMap<>();

	    // 이미 채팅방이 있는지 확인
	    Users sender = usersRepo.findById(senderId).orElse(null);
	    Users receiver = usersRepo.findById(receiverId).orElse(null);

	    if (sender != null && receiver != null) {
	        Chatroom existingRoom = chatroomRepo.findBySenderAndReceiver(sender, receiver);

	        if (existingRoom == null) {
	            // 채팅방이 없으면 새로 생성
	            Chatroom newRoom = new Chatroom();
	            newRoom.setSender(sender);
	            newRoom.setReceiver(receiver);
	            newRoom = chatroomRepo.save(newRoom);

	            map.put("message", "채팅을 연결합니다.");
	            map.put("chatroomNo", newRoom.getChatroomNo());
	            map.put("senderId", senderId);
	            map.put("receiverId", receiverId);
	        } else {
	            map.put("message", "상담 신청 내역이 있습니다, 이전 채팅방에 입장합니다.");
	            map.put("chatroomNo", existingRoom.getChatroomNo());
	            map.put("senderId", senderId);
	            map.put("receiverId", receiverId);
	            map.put("chathistory", chatRepo.findByChatroomOrderByChatDate(existingRoom));
	        }
	    } else {
	        map.put("message", "사용자 정보를 찾을 수 없습니다.");
	    }

	    return (T) map;
	}

	// 채팅리스트에서  회원이 채팅을 입장하는부분(재입장) 
	@GetMapping(value = { "/Reentry" })
	public <T> T userEChatRoom(HttpServletRequest request) {
		System.out.println("회원 채팅 입장 요청 들어옴");

		String roomnumber = request.getParameter("roomnumber");
		Integer roomNumber = Integer.parseInt(roomnumber); 
		
		Chatroom chatroom = chatroomRepo.findById(roomNumber).get();
		
		List<Chat> chatlist = chatRepo.findByChatroomOrderByChatDate(chatroom); 

		return (T) chatlist;
	}


}
