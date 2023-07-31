package org.matgyeojo.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name="PET_VACCINE")
public class PetVaccine {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer vaccineNo;	//예방접종 시퀀스
	//반려 시퀀스 FK
	@OneToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	@JoinColumn(name = "pet_no")
	PetProfile petprofile;
	
	@Column(nullable = false)
	private String vaccine1;	//접종상태1
	@Column(nullable = false)
	private String vaccine2;	//접종상태2
	@Column(nullable = true)
	private String vaccineMsg;	//건강 주의사항
}
