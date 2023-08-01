package org.matgyeojo.repository;

import org.matgyeojo.dto.GyeonggiHospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GyeonggiHospitalRepository extends JpaRepository<GyeonggiHospital, Integer> {
	//DB에서 도로명 주소의 값이 같은게 있는지 없는지 판단 있으면 true, 없으면 false  반환 없을경우에만 db에 저장하게 함
	@Query("SELECT COUNT(h) > 0 FROM GyeonggiHospital h WHERE h.REFINE_ROADNM_ADDR = :roadAddress")
	boolean existsByREFINE_ROADNM_ADDR(@Param("roadAddress") String roadAddress);
}
