package org.matgyeojo.dto;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.SQLUpdate;
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
@Table(name="REVIEW")
@Entity
public class Review {

	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)//auto 인데 테이블별로 따로
	private int reviewNo;//리뷰시퀀스
	
	//펫시터아이디 
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "user_id")
	private Users petsitter;
	
	
	
	@Column(nullable = false)
	private int reviewTime;//리뷰시간점수
	@Column(nullable = false)
	private int reviewKind;//리뷰친절점수
	@Column(nullable = false)
	private int reviewDelecacy;//리뷰섬세점수
	@Column(nullable = false)
	private String reviewMsg;//리뷰후기
	
	
}
