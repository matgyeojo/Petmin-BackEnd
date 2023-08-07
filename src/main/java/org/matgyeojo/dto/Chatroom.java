package org.matgyeojo.dto;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long chatroomId; // 채팅방 번호

	@ManyToOne
	@JoinColumn(name = "sender_id")
	private Users sender; // 보내는 사람

	@ManyToOne
	@JoinColumn(name = "receiver_id")
	private Users receiver; // 받는 사람

	@OneToMany(mappedBy = "chatroom", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonIgnore
	private List<Chat> chats;
}