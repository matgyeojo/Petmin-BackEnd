//package org.matgyeojo.service;
//
//import java.util.List;
//
//import org.matgyeojo.dto.Chat;
//import org.matgyeojo.dto.Chatroom;
//import org.matgyeojo.repository.ChatroomRepo;
//import org.springframework.stereotype.Service;
//
//@Service
//public class ChatroomService {
//
//    private final ChatroomRepo chatroomrepo;
//
//    public ChatroomService(ChatroomRepo chatroomRepository) {
//        this.chatroomrepo = chatroomRepository;
//    }
//
//    public List<Chat> getChatHistoryForChatroom(Chatroom chatroom) {
//        // Chatroom에 해당하는 이전 채팅 내역을 가져오는 로직을 구현합니다.
//        // 예를 들어, chatroom.getChatList() 등으로 채팅 내역을 가져올 수 있습니다.
//        // 필요에 따라 쿼리를 작성하여 가져오도록 구현해야 합니다.
//        return chatroom.getChatroomNo();
//    }
//}
