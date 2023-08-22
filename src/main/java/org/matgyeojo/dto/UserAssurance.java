package org.matgyeojo.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "USER_ASSURANCE")
@Builder
public class UserAssurance {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userAssurance_no; // 사용자 보험 시퀀스

	// 보험이름 FK
	@ManyToOne
	@JoinColumn(name = "assurance_name")
	@JsonIgnore
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Assurance assurance;

	// 돌봄 시퀀스
	@ManyToOne
	@JoinColumn(name = "dolbom_no")
	@JsonIgnore
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Dolbom dolbom;

}
