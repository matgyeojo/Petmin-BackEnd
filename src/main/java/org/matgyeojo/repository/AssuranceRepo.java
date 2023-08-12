package org.matgyeojo.repository;

import org.matgyeojo.dto.Assurance;
import org.springframework.data.repository.CrudRepository;

public interface AssuranceRepo extends CrudRepository<Assurance, String>{
	//보험 이름으로 찾기
	public Assurance findByAssuranceName(String assuranceName);
	
}
