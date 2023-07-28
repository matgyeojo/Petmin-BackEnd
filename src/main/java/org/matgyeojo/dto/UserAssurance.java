package org.matgyeojo.dto;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
	private Integer userAssurance_no; //사용자 보험 시퀀스
	//private String assuranceName; //보험이름 FK
	//private int dolbomNo;//돌봄 시퀀스
}
