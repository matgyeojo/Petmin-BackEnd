package org.matgyeojo.websocket;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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


@Service
@ServerEndpoint("/socket/chatt")
public class WebSocketChat {
	private static Map<Long, List<Session>> clients = Collections.synchronizedMap(new HashMap<>());

    private static Logger logger = LoggerFactory.getLogger(WebSocketChat.class);

    
    @Autowired
    ChatRepo chatRepo; 
    
    @Autowired
    ChatroomRepo chatroomRepo;
    
    @Autowired
    UsersRepo usersRepo;
    
    @OnOpen
    public void onOpen(Session session, @PathParam("room") Integer chatroomNo) {
        // DB에서 과거 채팅 내역을 clent에게 보내준다.
        Long room = chatroomNo.longValue();
        logger.info("open session : {}, clients={}", session.toString(), clients);
        Map<String, List<String>> res = session.getRequestParameterMap();
        logger.info("res={}", res);

        System.err.println("room : " + room);
        // 팅방 번호에 이미 클라이언트 세션이 연결되어 있는지 확인
        if (clients.containsKey(room)) {
            // 이미 채팅방에 클라이언트 세션이 존재하므로 해당 세션을 리스트에 추가
            clients.get(room).add(session);
        } else {
        	//연결되어 있지 않은 경우, 새로운 세션을 리스트에 추가하고 맵에 채팅방 번호와 함께 저장
            List<Session> ls = new ArrayList<Session>();
            ls.add(session);
            clients.put(room, ls);
        }
    }
    
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        System.err.println("웹소켓온메세지");
        
        // 들어온 채팅 메세지와 사용자 정보를 DB에 저장한다.
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(message);
        Integer chatNo = jsonNode.get("room").asInt(); //방 번호 추출
        String chatMsg = jsonNode.get("chatMsg").asText(); //메세지 추출
        String startId = jsonNode.get("startId").asText(); // 보내는 사람 추출
        String endId = jsonNode.get("endId").asText(); //받는 사람 추출
        String sentTimeStr = jsonNode.get("chatDate").asText(); // 보낸 시간 추출
        
        
        System.err.println("chatNo : " + chatNo);
        
        // ChatroomRepo를 사용하여 채팅방 정보를 가져온다.
        Optional<Chatroom> chatroomOptional = chatroomRepo.findById(chatNo);
        if (chatroomOptional.isPresent()) {
            Chatroom chatroom = chatroomOptional.get();
            
            // 사용자 아이디로 Users 엔티티 조회
            Optional<Users> sender = usersRepo.findById(startId);
            Optional<Users> receiver = usersRepo.findById(endId);
            
            if (!sender.isPresent() || !receiver.isPresent()) {
               System.out.println("올바르지 않은 발신자 또는 수신자");
               return;
            }
            
            // 보낸 시간을 Timestamp 형태로 변환
            Timestamp sentTime = Timestamp.valueOf(sentTimeStr);
            
            // Chat 엔티티 생성 및 DB저장
            Chat chat = new Chat();
            chat.setChatroom(chatroom);
            chat.setStartId(sender.get());
            chat.setEndId(receiver.get()); 
            chat.setChatMsg(chatMsg);
            chat.setChatImg(null);
            chat.setChatDate(sentTime); 
            chat.setChatCheck(false);
            chatRepo.save(chat);
	            
            // 방에 참여 중인 모든 클라이언트들에게 메시지를 보낸다.
            logger.info("receive message : {}", message);
            
            List<Session> sessionList = clients.get(chatNo.longValue());
            if (sessionList != null && !sessionList.isEmpty()) {
                for (Session s : sessionList) {
                    try {
                        s.getBasicRemote().sendText(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                System.out.println("이 채팅방에 클라이언트가 없습니다");
            }
        } else {
            System.out.println("채팅방을 찾을 수 없습니다");
        }
    }
		
    @OnClose // 클라이언트가 접속을 종료할 시
    public void onClose(Session session, @PathParam("room") Integer chatroomNo) {
        logger.info("session close : {}", session);
        Long room = chatroomNo.longValue();
        clients.remove(room);
    }
}