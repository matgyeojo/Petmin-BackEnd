package org.matgyeojo.dto;

import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

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
@Table(name = "HOSPITAL")
public class GyeonggiHospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer hospitalNo; //병원 시퀀스
    @Column(name = "hospitalName")
    private String BIZPLC_NM; //병원 이름
    @Column(name = "hospitalLatitude")
    private Double REFINE_WGS84_LAT; //병원 위도
    @Column(name = "hospitalLongtitude")
    private Double REFINE_WGS84_LOGT; //병원 경도
    @Column(name = "hospitalAddress")
    private String REFINE_ROADNM_ADDR; //병원 주소
    @Column(name = "hospiralState")
    private String BSN_STATE_NM; //병원 상태
    @Column(name = "hospitalTel")
    private String LOCPLC_FACLT_TELNO; //병원 전화번호 
    @Column(name = "hospitalPartner")
    private Boolean hospitalPartner; //병원 보험제휴
    
 // 자동으로 랜덤 값으로 hospitalPartner를 설정하는 메서드
    public void setRandomHospitalPartner() {
        Random random = new Random();
        int randomInt = random.nextInt(100); // 0부터 99까지의 랜덤한 정수 생성

        if (randomInt < 40) {
            this.hospitalPartner = true; // 40%의 확률로 true(1)로 설정
        } else {
            this.hospitalPartner = false; // 60%의 확률로 false(0)로 설정
        }
    }
}