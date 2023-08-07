package org.matgyeojo.dto;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS")
@Entity

public class Users {

	@Id
	private String userId;// 유저 PK
	@Column(nullable = false)
	private String userPass; // 유저 비밀번호
	@Column(nullable = true)
	private String userName;//유저이름
	@Column(nullable = true)
	private int userAge;//유저나이
	@Column(nullable = true)
	private String userAddress;//유저주소
	@Column(nullable = true)
	private String userSex;//유저성별
	@Column(nullable = true,unique = true)
	private String userCard;//유저카드번호
	@Column(nullable = true)
	private int userCardpass;//유저카드비밀번호
	@Column(nullable = true)
	private String userImg;//유저 사진
	@Column(nullable = false)
	private String userLicence;
	@UpdateTimestamp
	private Timestamp userUpdateTime;//유저마지막업데이트날짜

	@OneToOne(mappedBy = "users",cascade = CascadeType.ALL)
	@JsonIgnore
	private PetsitterProfile petsitterProfile;

	// 연관관계설정:1:n
	// mappedBy
	//채팅방
    @OneToMany(mappedBy = "sender")
    @JsonIgnore
    private List<Chatroom> sentChatrooms; // 변경된 이름

    @OneToMany(mappedBy = "receiver")
    @JsonIgnore
    private List<Chatroom> receivedChatrooms; // 변경된 이름

	//선호필터
	@OneToMany( mappedBy = "user")
	@JsonIgnore
	private List<Preference> preferences;
	//알람
	@OneToMany( mappedBy = "user")
	@JsonIgnore
	private List<Alarm> alarms;
	//채팅
	@OneToMany( mappedBy = "startId")
	@JsonIgnore
	private List<Chat> chatStarts;
	@OneToMany( mappedBy = "endId")
	@JsonIgnore
	private List<Chat> chatEnds;
	//펫 프로필
	@OneToMany( mappedBy = "user")
	@JsonIgnore
	private List<PetProfile> petProfiles;
	//즐겨찾기
	@OneToMany( mappedBy = "user")
	@JsonIgnore
	private List<PetSub> users;
	@OneToMany( mappedBy = "sitter")
	@JsonIgnore
	private List<PetSub> sitters;
	//돌봄
	@OneToMany(mappedBy="user1")//사용자
	@JsonIgnore
	private List<Dolbom> dolbom1;
	@OneToMany(mappedBy="user2")//펫시터
	@JsonIgnore
	private List<Dolbom> dolbom2;
	
	
}