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
	@OnDelete(action = OnDeleteAction.CASCADE)
	Users user1;
	//펫시터 아이디 fk
	@ManyToOne
	@JoinColumn(name="petsitter_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	Users user2;
	
	@UpdateTimestamp
	private Timestamp scheduleDay;// 펫시터 가능한 일
	@UpdateTimestamp
	private Timestamp scheduleHour;// 펫시터 가능한 시간
	@Column(nullable = true)
	private Boolean dolbomStatus;// 예약여부
	@Column(nullable = true)
	private String dolbomOption;// 돌봄 종류
	
	
	@OneToOne(mappedBy = "dolbomNo")
	private UserAssurance userAssurance;

}
