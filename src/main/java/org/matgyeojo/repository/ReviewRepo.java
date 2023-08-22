package org.matgyeojo.repository;

import java.util.List;

import org.matgyeojo.dto.Review;
import org.matgyeojo.dto.Users;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepo extends CrudRepository<Review, Integer>{

	//펫시터의 리뷰찾기
	public List<Review> findByPetsitter(Users user);
	
}
