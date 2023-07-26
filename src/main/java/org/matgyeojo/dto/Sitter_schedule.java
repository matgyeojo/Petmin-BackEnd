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
@Table(name="SITTER_SCHEDULE")

public class Sitter_schedule {
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private int scheduleNo;

	@Column(nullable = true)
	private String sitterOkday;
}
