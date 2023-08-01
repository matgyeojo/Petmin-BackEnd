package org.matgyeojo.dto;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "USER_ASSURANCE")
public class UserAssurance {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userAssurance_no; // 사용자 보험 시퀀스

	// 보험이름 FK
	@MapsId
	@OneToOne
	@JoinColumn(name = "assurance_name")
	@JsonIgnore
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Assurance assuranceName;
	// 돌봄 시퀀스
	@MapsId
	@OneToOne
	@JoinColumn(name = "dolbom_no")
	@JsonIgnore
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Dolbom dolbomNo;

}
