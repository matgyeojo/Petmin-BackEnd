package org.matgyeojo.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name="PET_PROFILE")
public class PetProfile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer petNo;	//반려 시퀀스
	//반려 아이디	FK
	@Column(nullable = false)
	private String petName;	//반려 이름
	@Column(nullable = false)
	private Integer petAge;	//반려 나이
	@Column(nullable = false)
	private String petSpecies;	//반려 종
	@Column(nullable = false)
	private Double petWeight;	//반려 몸무게
	@Column(nullable = false)
	private String petSex;	//반려 성별
	@Column(nullable = true)
	private String petImg;//펫이미지
}
