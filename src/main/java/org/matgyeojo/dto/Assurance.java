package org.matgyeojo.dto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Table(name = "ASSURANCE")
public class Assurance {
	@Id
	private String assuranceName; //보험이름
	private Integer assurancePrice; //납부금
	private Integer assuranceDc; //할인률
	private Integer assuranceMaxmoney; //최대 지원액수
	private String assuranceUsertype; //사용자 유형
}
