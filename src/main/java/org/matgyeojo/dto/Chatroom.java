package org.matgyeojo.dto;

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
@Table(name = "CHATROOM")
public class Chatroom {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer chatroomNo;	//채팅방 시퀀스
	private String userId1;	//유저 아이디
	private String userId2;	//유저 아이디2
}
