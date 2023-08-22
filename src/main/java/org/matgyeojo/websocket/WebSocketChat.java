package org.matgyeojo.websocket;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.matgyeojo.dto.Chat;
import org.matgyeojo.dto.Chatroom;
import org.matgyeojo.dto.Users;
import org.matgyeojo.repository.ChatRepo;
import org.matgyeojo.repository.ChatroomRepo;
import org.matgyeojo.repository.UsersRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
@ServerEndpoint(value = "/socket/chatt/{room}", configurator = ServerEndpointConfigurator.class)
public class WebSocketChat {
   private static Map<Long, Set<Session>> clients = new ConcurrentHashMap<>();

   private static Logger logger = LoggerFactory.getLogger(WebSocketChat.class);

   @Autowired
   ChatRepo chatRepo;

   @Autowired
   ChatroomRepo chatroomRepo;

   @Autowired
   UsersRepo userRepo;

   @OnOpen // 클라이언트가 접속할 때 마다 실행
   public void onOpen(Session session, @PathParam("room") String chatroomId) {
	   System.out.println("들어왔다");
      // DB에서 과거 채팅 내역을 client에게 보내준다.
      System.out.println(chatroomId);
      Long room = Long.parseLong(chatroomId);
      logger.info("open session : {}, clients={}", session.toString(), clients);
      Map<String, List<String>> res = session.getRequestParameterMap();
      logger.info("res={}", res);

      // 채팅방에 이미 연결된 세션이 있는지 확인
      if (clients.containsKey(room)) {
         // 이미 연결된 세션이 있을 경우 새로운 세션만 추가
         clients.get(room).add(session);
         List<Chat> existingMessages = chatRepo.findByChatroom_chatroomId(room);
         for (Chat existingMessage : existingMessages) {
            existingMessage.setChatCheck(true);
            chatRepo.save(existingMessage);
         }

      } else {
         // 연결된 세션이 없을 경우 새로운 Set을 생성하고 세션을 추가
         Set<Session> uniqueSessions = new HashSet<>();
         uniqueSessions.add(session);
         clients.put(room, uniqueSessions);
         List<Chat> existingMessages = chatRepo.findByChatroom_chatroomId(room);
         for (Chat existingMessage : existingMessages) {
            existingMessage.setChatCheck(true);
            chatRepo.save(existingMessage);
         }
      }
   }

   @OnMessage // 메세지 수신 시 //{}
   public void onMessage(String message, Session session) throws IOException {
      System.out.println(session);
      ObjectMapper mapper = new ObjectMapper();
      JsonNode jsonNode = mapper.readTree(message);
      String msg = jsonNode.get("msg").asText();
      String startIdString = jsonNode.get("startId").asText();
      String receiverId = jsonNode.get("receiverId").asText();
//      String chatImgUrl = jsonNode.get("chatImgUrl").asText();
      Long chatroomId = jsonNode.get("chatroomId").asLong();

      Chatroom chatroom = chatroomRepo.findById(chatroomId).orElse(null);
      System.out.println(chatroom); ///
      if (chatroom == null) {
         System.out.println("no chat room");
         return;
      }

      // 보내는 사람 가져오기
      Users startUser = userRepo.findById(startIdString).orElse(null);
      if (startUser == null) {
         System.out.println("no user found");
         return;
      }

      // 받는 사람 가져오기
      Users receiverUser = userRepo.findById(receiverId).orElse(null);
      if (receiverUser == null) {
         System.out.println("no user found");
         return;
      }
      // Chat 객체 생성 및 저장
      Chat chat = Chat.builder().startId(startUser).endId(receiverUser).msg(msg).chatroom(chatroom).chatCheck(false)
//            .chatImg(chatImgUrl)
            .build();

      // 방에 참여 중인 모든 클라이언트들에게 메시지를 보낸다.
      logger.info("receive message : {}", message);

      Set<Session> uniqueSessions = new HashSet<>(clients.get(chatroomId));
      System.out.println("uniqueSessions:" + uniqueSessions);

      if (uniqueSessions.size() == 1) {
         System.out.println("null");
         chat.setChatCheck(false);
      } else {

          System.out.println("2");
         chat.setChatCheck(true);
      }

      ((ObjectNode) jsonNode).put("chatcheck", chat.getChatCheck());
      message = jsonNode.toString();
      System.out.println(message);

      for (Session s : uniqueSessions) {
         System.out.println("데이터 전송시작");
         logger.info("메세지 : test send data : {}", message);
         System.out.println("메세지 : " + message);
         s.getBasicRemote().sendText(message);
         System.out.println("데이터 전송완료");
      }

      chatRepo.save(chat);
   }

   @OnClose // 클라이언트가 접속을 종료할 시
   public void onClose(Session session, @PathParam("room") Integer chatroomNo) {
      logger.info("!!!!session close : {}", session);
      Long room = chatroomNo.longValue();

      // 제대로 초기화된 clients 컬렉션에서 세션 제거
      System.out.println("clients:" + clients);
      if (clients.get(room).size() == 1) {
         System.out.println("방닫음");
         clients.remove(room);
      } else {
         System.out.println("session지우기:" + session);
         clients.get(room).remove(session);
      }
   }
}