package org.matgyeojo.dto;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
@Entity
@Table(name="PETSITTER_PROFILE")

public class PetsitterProfile {
	
	@Id
	private int userid;//펫싵터 시퀀스
	
	@MapsId
	@OneToOne
	@JoinColumn(name = "user_id")
	Users users;
	
	@Column(nullable = false)
	private String sitterHouse;//펫시터 집 이미지
	@Column(nullable = false)
	private String sitterHousetype;//펫시티 거주형태
	@Column(nullable = false)
	private String sitterTem;//펫시터 온도
	@Column(nullable = false)
	private String sitterMsg;//펫시터 자기소개
	

	
}
