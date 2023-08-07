package org.matgyeojo.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@ServerEndpoint(value = "/socket/chatt/{room}", configurator = ServerEndpointConfigurator.class)
public class WebSocketChat {
	private static Map<Long, List<Session>> clients = Collections.synchronizedMap(new HashMap<>());

	private static Logger logger = LoggerFactory.getLogger(WebSocketChat.class);

	@Autowired
	ChatRepo chatRepo;

	@Autowired
	ChatroomRepo chatroomRepo;

	@Autowired
	UsersRepo userRepo;

	@OnOpen // 클라이언트가 접속할 때 마다 실행
	public void onOpen(Session session, @PathParam("room") String chatroomId) {
		// DB에서 과거 채팅 내역을 client에게 보내준다.
		Long room = Long.parseLong(chatroomId);
		logger.info("open session : {}, clients={}", session.toString(), clients);
		Map<String, List<String>> res = session.getRequestParameterMap();
		logger.info("res={}", res);

		// 채팅방에 이미 연결된 세션이 있는지 확인
		if (clients.containsKey(room)) {
			// 이미 연결된 세션이 있을 경우 새로운 세션만 추가
			clients.get(room).add(session);
		} else {
			// 연결된 세션이 없을 경우 새로운 리스트를 생성하고 세션을 추가
			List<Session> ls = new ArrayList<Session>();
			ls.add(session);
			clients.put(room, ls);
		}
	}

	@OnMessage // 메세지 수신 시
	public void onMessage(String message, Session session) throws IOException {
		System.err.println("웹소켓온메세지");

		// JSON 문자열을 파싱하여 필요한 정보를 추출
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode = mapper.readTree(message);
		String chatMsg = jsonNode.get("chatMsg").asText();
		String startIdString = jsonNode.get("startId").asText();
		String receiverId = jsonNode.get("receiverId").asText();
		String chatImgUrl = jsonNode.get("chatImgUrl").asText();
		Long chatId = Long.parseLong(jsonNode.get("room").asText());

		Chatroom chatroom = chatroomRepo.findById(chatId).orElse(null);
		if (chatroom == null) {
			System.out.println("no chat room");
			return;
		}

		Users startUser = userRepo.findById(startIdString).orElse(null);
		if (startUser == null) {
			System.out.println("no user found");
			return;
		}
		Users receiverUser = userRepo.findById(receiverId).orElse(null);
		if (receiverUser == null) {
			System.out.println("no user found");
			return;
		}

		Chat chat = Chat.builder().startId(startUser).endId(receiverUser).chatMsg(chatMsg).chatroom(chatroom)
				.chatImg(chatImgUrl) // 이미지 URL 설정
				.build();

		chatRepo.save(chat);

		// 방에 참여 중인 모든 클라이언트들에게 메시지를 보낸다.
		logger.info("receive message : {}", message);

		// 참여중인 클라이언트에게 전송하자
		List<Session> sessionList = clients.get(chatId);
		System.err.println("slist : " + sessionList);
		for (Session s : sessionList) {
			System.out.println("데이터 전송시작");
			logger.info("메세지 : test send data : {}", message);
			System.out.println("메세지 : " + message);
			s.getBasicRemote().sendText(message);
			System.out.println("데이터 전송완료");
		}
	}

	@OnClose // 클라이언트가 접속을 종료할 시
	public void onClose(Session session, @PathParam("room") Integer chatroomNo) {
		logger.info("session close : {}", session);
		Long room = chatroomNo.longValue();
		clients.remove(room);
	}
}