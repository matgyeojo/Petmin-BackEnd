package org.matgyeojo.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PREFERENCE")
public class Preference {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer preferenceNo; // 선호 시퀀스

	// 연관관계 설정n:1
	// FK: 킬람이 board_bnofh 로 생성된다
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	//@JsonIgnore
	@JoinColumn(name = "user_id")
	private Users user;

	@Column(nullable = false)
	private String preference1; // 첫번째 선호
	@Column(nullable = false)
	private String preference2; // 두번째 선호
	@Column(nullable = false)
	private String preference3; // 세번째 선호
	@Column(nullable = false)
	private String preference4; // 세번째 선호
	@Column(nullable = false)
	private String preference5; // 세번째 선호

}
