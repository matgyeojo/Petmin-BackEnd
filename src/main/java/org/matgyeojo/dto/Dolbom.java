package org.matgyeojo.dto;


import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
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
@Table(name="DOLBOM")
@Entity
public class Dolbom {

	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)//auto 인데 테이블별로 따로
	private int dolbomNo;//돌콤시퀀스
	
	@UpdateTimestamp
	private Timestamp scheduleDay;//펫시터 가능한 일
	@UpdateTimestamp
	private Timestamp scheduleHour;//펫시터 가능한 시간
	@Column(nullable = true)
	private Boolean dolbomStatus;//예약여부
	@Column(nullable = true)
	private String dolbomOption;//돌봄 종류
	
	//user_id 유저 아이디	fk 
	//petsitter_id 펫시터 아이디 fk
}
