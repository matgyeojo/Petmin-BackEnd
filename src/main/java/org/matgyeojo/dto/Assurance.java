package org.matgyeojo.dto;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
	@Column(nullable =	false)
	private Integer assurancePrice; //납부금
	@Column(nullable =	false)
	private Integer assuranceDc; //할인률
	@Column(nullable =	false)
	private Integer assuranceMaxmoney; //최대 지원액수
	@Column(nullable =	false)
	private String assuranceUsertype; //사용자 유형
	
	@OneToOne(mappedBy = "assuranceName")
	private UserAssurance userAssurance;
}
