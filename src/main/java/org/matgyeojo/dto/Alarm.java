package org.matgyeojo.dto;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
@Table(name="ALARM")
@Entity
public class Alarm {

	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)//auto 인데 테이블별로 따로
	private int alarmNo;//알림시퀀스
	
	//userId 유저 아이디 fk
	
	@Column(nullable = true)
	private String alarmMsg;//알림내용
	@Column(nullable = true)
	private Boolean alarmState;//알림읽음?
	@CreationTimestamp
	private Timestamp alarmDate;//알림날짜
	
}
