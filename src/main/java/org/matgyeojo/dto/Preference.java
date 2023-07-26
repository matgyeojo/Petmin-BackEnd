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
@Table(name="PREFERENCE")
public class Preference {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer preferenceNo;	//선호 시퀀스
	//유저 아이디	FK
	private String preference1;	//첫번째 선호
	private String preference2;	//두번째 선호
	private String preference3;	//세번째 선호
}
