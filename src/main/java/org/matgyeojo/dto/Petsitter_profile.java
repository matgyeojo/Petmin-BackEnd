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
@Table(name="PETSITTER_PROFILE")

public class Petsitter_profile {
	
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private int sitterNo;
	
	
	@Column(nullable = true)
	private String sitterHouse;
	@Column(nullable = true)
	private String sitterHousetype;
	@Column(nullable = true)
	private String sitterPickup;
	@Column(nullable = true)
	private int sitterId;
	@Column(nullable = true)
	private String sitterMsg;
	
}
