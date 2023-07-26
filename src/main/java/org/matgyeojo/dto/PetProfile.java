package org.matgyeojo.dto;

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
@Table(name="PET-PROFILE")
public class PetProfile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer petNo;	//반려 시퀀스
	//반려 아이디	FK
	private String petName;	//반려 이름
	private Integer petAge;	//반려 나이
	private String petSpecies;	//반려 종
	private Double petWeight;	//반려 몸무게
	private String petSex;	//반려 성별
}
