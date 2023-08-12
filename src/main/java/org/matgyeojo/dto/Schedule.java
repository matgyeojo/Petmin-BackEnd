package org.matgyeojo.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
@Table(name = "SCHEDULE")
@Entity
public class Schedule {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto 인데 테이블별로 따로
	private int scheduleNo;// 돌콤시퀀스

	
	//펫시터 아이디 fk
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="petsitter_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	Users user;
	
	
	@Column(nullable = false)
	private String scheduleDay;// 펫시터 가능한 날
	@Column(nullable = false)
	private String scheduleHour;// 펫시터 가능한 시간
	@Column(nullable = true)
	private int dolbomStatus;// 스케쥴 상태
	@Column(nullable = true)
	private String dolbomOption;// 돌봄 종류
	
}
