package org.matgyeojo.dto;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "CHAT")
public class Chat {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer chatNo; //채팅내용 시퀀스
	//private Integer chatroomNo; //채팅방 시퀀스 FK
	private String startId; //보내는 사람 아이디
	private String endId; //받는 사람 아이디
	private String chatMsg; //채팅 내용
	private String chatImg; //채팅 이미지
	private Timestamp chatDate; //채팅 보낸 날짜
	private Boolean chatCheck; //채팅 읽었나요
}
