package org.matgyeojo.dto;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "DOLBOM")
@Entity
public class Dolbom {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto 인데 테이블별로 따로
	private int dolbomNo;// 돌콤시퀀스

	//유저 아이디 fk
	@ManyToOne
	@JoinColumn(name="user_id")
	@JsonIgnore
	@OnDelete(action = OnDeleteAction.CASCADE)
	Users user1;
	//펫시터 아이디 fk
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="petsitter_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	Users user2;
	
	//펫시퀀스
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	@JoinColumn(name = "pet_no")
	PetProfile petProfile;
	
	@Column(nullable = false)
	private String scheduleDay;// 펫시터 가능한 일
	@Column(nullable = false)
	private String scheduleHour;// 펫시터 가능한 시간
	@Column(nullable = true)
	private int dolbomStatus;// 예약여부
	@Column(nullable = true)
	private String dolbomOption;// 돌봄 종류
	
	
	@OneToOne(mappedBy = "dolbomNo")
	@JsonIgnore
	private UserAssurance userAssurance;

}
