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
	
	@CreationTimestamp
	private Timestamp dolbomAsk;//돌봄신청날짜
	@UpdateTimestamp
	private Timestamp dolbomStart;//돌봄시작날짜
	@UpdateTimestamp 
	private Timestamp dolbomEnd;//돌봄끝날짜
	
	//user_id 유저 아이디	fk 
	//petsitter_id 펫시터 아이디 fk
}
