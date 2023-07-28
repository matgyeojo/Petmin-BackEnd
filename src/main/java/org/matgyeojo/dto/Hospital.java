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
@Table(name = "HOSPITAL")
public class Hospital {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer hospitalNo; //병원 시퀀스
	private String hospitalName; //병원 이름
	private Double hospitalLatitude; //병원 위도
	private Double hospitalLongtitude; //병원 경도
	private String hospitalAddress; //병원 주소
	private String hospiralState; //병원 상태
	private String hospitalTel; //병원 전화번호
	private Boolean hospitalPartner; //병원 보험제휴
}
