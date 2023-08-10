package org.matgyeojo.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.matgyeojo.dto.PetProfile;
import org.matgyeojo.dto.PetTendency;
import org.matgyeojo.dto.PetVaccine;
import org.matgyeojo.dto.Users;
import org.matgyeojo.repository.PetProfileRepo;
import org.matgyeojo.repository.PetTendencyRepo;
import org.matgyeojo.repository.PetVaccineRepo;
import org.matgyeojo.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PetProfileService {
	@Autowired
	PetProfileRepo PetProfileRepo;
	
	@Autowired
	UsersRepo UsersRepo;
	
	@Autowired
	PetTendencyRepo PetTendencyRepo;
	
	@Autowired
	PetVaccineRepo PetVaccineRepo;
	
	@Autowired
	S3Uploader s3uploader;
	
	public String petprofileSave(String userId, 
			 String petName, 
			 int petAge,
			 String petSpecies,
			 double petWeight,
			 String petSex,
			 MultipartFile petImg,
			 String petMsg) throws IOException {
		
		Users user = UsersRepo.findById(userId).orElse(null);
		
		if(user != null) {
			String image_list = "";

			
			if (petImg != null && !petImg.isEmpty()) {
				String sotredFileName = s3uploader.upload(petImg, "pet");
				image_list += (sotredFileName);
			}
		
		PetProfile pet = PetProfile.builder()
				.petName(petName)
				.petAge(petAge)
				.petSpecies(petSpecies)
				.petWeight(petWeight)
				.petSex(petSex)
				.user(user)
				.petImg(image_list)
				.build();
			PetProfileRepo.save(pet);
			
			return "펫 프로필 저장 성공";
		} else {
			return "펫 프로필 저장 실패";
		}
		
	}
	
	public String petTendencySave(PetTendency dto, Integer petNo) {
		PetProfile petprofile = PetProfileRepo.findById(petNo).orElse(null);
		if(petprofile != null) {
			if(petprofile.getPetTendency() == null) {
				dto.setPetprofile(petprofile);
				PetTendencyRepo.save(dto);		
			}
			return "펫 성향 저장 성공";	
		} else {
			return "펫 성향 저장 실패";
		}
	}

	public String petVaccineSave(PetVaccine dto, Integer petNo) {
		PetProfile petprofile = PetProfileRepo.findById(petNo).orElse(null);
		if(petprofile != null) {
			if(petprofile.getPetVaccine() == null) {
				dto.setPetprofile(petprofile);
				PetVaccineRepo.save(dto);
			}
			return "펫 예방접종 정보 저장 성공";
		} else {
			return "펫 예방접종 정보 저장 실패";
		}
	}

	public List<PetProfile> petProfileList(String userId) {
		Users user = UsersRepo.findById(userId).orElse(null);
		return PetProfileRepo.findByUser(user);
	}

	public Optional<PetProfile> petInformation(Integer petNo) {
		return PetProfileRepo.findById(petNo);
	}
	
	public String petTendencyUpdate(PetTendency dto, Integer tendenctNo) {
		PetTendency petTendency = PetTendencyRepo.findById(tendenctNo).orElse(null);
		if (petTendency != null) {
			petTendency.setTendency1(dto.getTendency1());
			petTendency.setTendency2(dto.getTendency2());
			petTendency.setTendency3(dto.getTendency3());
			petTendency.setTendency4(dto.getTendency4());
			petTendency.setTendency5(dto.getTendency5());
			petTendency.setTendencyMsg(dto.getTendencyMsg());
			
			PetTendencyRepo.save(petTendency);
			return "펫 성향 정보 수정 성공";
		} else {
			return "펫 성향 정보 수정 실패";
		}
		
	}

	public String petVaccineUpdate(PetVaccine dto, Integer vaccineNo) {
		PetVaccine petVaccine = PetVaccineRepo.findById(vaccineNo).orElse(null);
		if(petVaccine != null) {
			petVaccine.setVaccine1(dto.getVaccine1());
			petVaccine.setVaccine2(dto.getVaccine2());
			petVaccine.setVaccineMsg(dto.getVaccineMsg());
			
			PetVaccineRepo.save(petVaccine);
			return "펫 예방접종 정보 수정 성공";
		} else {
			return "펫 예방접종 정보 수정 실패";
		}
		
	}

	public String petInformationUpdate(PetProfile dto, Integer petNo) {
		PetProfile petProfile = PetProfileRepo.findById(petNo).orElse(null);
		if(petProfile != null) {
			petProfile.setPetName(dto.getPetName());
			petProfile.setPetAge(dto.getPetAge());
			petProfile.setPetSpecies(dto.getPetSpecies());
			petProfile.setPetWeight(dto.getPetWeight());
			petProfile.setPetSex(dto.getPetSex());
			petProfile.setPetImg(dto.getPetImg());
			petProfile.setPetMsg(dto.getPetMsg());
			
			PetProfileRepo.save(petProfile);
			return "펫 프로필 정보 수정 성공";
		} else {
			return "펫 프로필 정보 수정 실패";
		}
	}

	public String petInformainDelete(Integer petNo) {
		PetProfile petProfile = PetProfileRepo.findById(petNo).orElse(null);
		if(petProfile != null) {
			PetProfileRepo.deleteById(petNo);
			return "삭제 성공";
		} else {
			return "삭제 성공";
		}
	}

}
