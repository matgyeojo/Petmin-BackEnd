package org.matgyeojo.dto;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	@Column(nullable = true)
	private String userPass; // 유저 비밀번호
	@Column(nullable = true)
	private String userName;// 유저이름
	@Column(nullable = true)
	private Integer userAge;// 유저나이
	@Column(nullable = true)
	private String userAddress;// 유저주소
	@Column(nullable = true)
	private String userSex;// 유저성별
	@Column(nullable = true)
	private String userDetailAddress; // 유저 상세주소	
	@Column(nullable = true,unique = true)
	private String userCard;//유저카드번호
	private Integer userCardpass;// 유저카드비밀번호
	@Column(nullable = true)
	private String userImg;// 유저 사진
	@Column(nullable = true)
	private String userLicence;
	@Column(nullable = true)
	private String userEmail;
	@UpdateTimestamp
	private Timestamp userUpdateTime;// 유저마지막업데이트날짜

	@OneToOne(mappedBy = "users", cascade = CascadeType.ALL)
	@JsonIgnore
	private PetsitterProfile petsitterProfile;

	   // 연관관계설정:1:n
	   // mappedBy
	   // 채팅방
	   @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
	   @JsonIgnore
	   private List<Chatroom> sentChatrooms; // 변경된 이름
	   @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY)
	   @JsonIgnore
	   private List<Chatroom> receivedChatrooms; // 변경된 이름

	// 선호필터
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Preference> preferences;
	// 알람
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Alarm> alarms;
	// 채팅
	@OneToMany(mappedBy = "startId", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Chat> chatStarts;
	@OneToMany(mappedBy = "endId", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Chat> chatEnds;
	// 펫 프로필
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<PetProfile> petProfiles;
	// 즐겨찾기
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<PetSub> users;
	@OneToMany(mappedBy = "sitter", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<PetSub> sitters;
	// 돌봄
	@OneToMany(mappedBy = "user1", fetch = FetchType.LAZY) // 사용자
	@JsonIgnore
	private List<Dolbom> dolbom1;
	@OneToMany(mappedBy = "user2", fetch = FetchType.LAZY) // 펫시터
	@JsonIgnore
	private List<Dolbom> dolbom2;
	//스케쥴
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY) // 펫시터
	@JsonIgnore
	private List<Schedule> schedule;

}