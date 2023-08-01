package org.matgyeojo.dto;

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
@Entity
@Table(name = "PET_PROFILE")
public class PetProfile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer petNo; // 반려 시퀀스
	// 반려 아이디 FK
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	@JoinColumn(name = "user_id")
	private Users user;

	@Column(nullable = false)
	private String petName; // 반려 이름
	@Column(nullable = false)
	private Integer petAge; // 반려 나이
	@Column(nullable = false)
	private String petSpecies; // 반려 종
	@Column(nullable = false)
	private Double petWeight; // 반려 몸무게
	@Column(nullable = false)
	private String petSex; // 반려 성별
	@Column(nullable = true)
	private String petImg;// 펫이미지

	// 펫 접종이랑 1대1
	@OneToOne(mappedBy = "petprofile")
	private PetVaccine petVaccine;

	// 펫 성향이랑 1대1
	@OneToOne(mappedBy = "petprofile")
	private PetTendency petTendency;
	
	//돌봄
	@OneToMany(mappedBy="petProfile")
	private List<Dolbom> dolbom;
}
