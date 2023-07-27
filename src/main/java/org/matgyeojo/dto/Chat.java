package org.matgyeojo.dto;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

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
//	private String startId; //보내는 사람 아이디 fk
//	private String endId; //받는 사람 아이디 fk
	@Column(nullable = false)
	private String chatMsg; //채팅 내용
	@Column(nullable = true)
	private String chatImg; //채팅 이미지
	@CreationTimestamp
	private Timestamp chatDate; //채팅 보낸 날짜
	@Column(nullable = false)
	private Boolean chatCheck; //채팅 읽었나요
}
