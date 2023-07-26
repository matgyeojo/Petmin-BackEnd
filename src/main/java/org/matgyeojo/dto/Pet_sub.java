package org.matgyeojo.dto;

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
@Table(name="PET_SUB")
@Entity
public class Pet_sub {
	
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)//auto 인데 테이블별로 따로
	private int subNo;//즐겨찾기 시퀀스
	
	//userID 유저 아이디 fk
	//sitterID 펫시터아이디 fk

}
