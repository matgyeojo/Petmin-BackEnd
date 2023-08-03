package org.matgyeojo.service;

import java.io.IOException;
import java.util.List;

import org.matgyeojo.dto.PetProfile;
import org.matgyeojo.dto.Users;
import org.matgyeojo.repository.PetProfileRepo;
import org.matgyeojo.repository.PetsitterProfileRepo;
import org.matgyeojo.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Service
public class PetService {

	@Autowired
	S3Uploader s3uploader;
	@Autowired
	UsersRepo userrepo;
	@Autowired
	PetsitterProfileRepo petsitterrepo;
	@Autowired
	PetProfileRepo petrepo;

	// 펫프로필 생성
	public Integer petInsert(String userId, String petName, MultipartFile[] petImgs) throws IOException {
		Users user = userrepo.findById(userId).orElse(null);
		
		String image_list = "[";

		for (MultipartFile img : petImgs) {
			if (img != null && !img.isEmpty()) {
				String sotredFileName = s3uploader.upload(img, "pet");
				image_list += (sotredFileName + ",");
			}
		}

		// 미지막에 , 빼주기
		image_list = image_list.substring(0, image_list.length() - 1);
		// 펫프로필 생성
		PetProfile pet = PetProfile.builder()
				.petName(petName)
				.petAge(6)
				.petSpecies("포메라니안")
				.petWeight(6.17)
				.petSex("남아")
				.user(user)
				.petImg(image_list+"]")
				.build();

		petrepo.save(pet);

		return pet.getPetNo();
	}

}
