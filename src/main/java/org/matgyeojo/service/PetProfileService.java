package org.matgyeojo.service;

import org.matgyeojo.dto.PetProfile;
import org.matgyeojo.dto.PetTendency;
import org.matgyeojo.dto.Users;
import org.matgyeojo.repository.PetProfileRepo;
import org.matgyeojo.repository.PetTendencyRepo;
import org.matgyeojo.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetProfileService {
	@Autowired
	PetProfileRepo PetProfileRepo;
	
	@Autowired
	UsersRepo UsersRepo;
	
	@Autowired
	PetTendencyRepo PetTendencyRepo;
	
	public void petprofileSave(PetProfile dto) {
		Users userId = UsersRepo.findById(dto.getUser().getUserId()).get();
		dto.setUser(userId);
		PetProfileRepo.save(dto);
	}

	public void petTendencySave(PetTendency dto) {
		PetProfile petNo = PetProfileRepo.findById(dto.getPetprofile().getPetNo()).get();
		dto.setPetprofile(petNo);
		PetTendencyRepo.save(dto);
	}
}
