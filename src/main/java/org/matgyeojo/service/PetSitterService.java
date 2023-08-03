package org.matgyeojo.service;

import java.io.IOException;

import org.matgyeojo.dto.Dolbom;
import org.matgyeojo.dto.PetsitterProfile;
import org.matgyeojo.dto.Users;
import org.matgyeojo.repository.DolbomRepo;
import org.matgyeojo.repository.PetProfileRepo;
import org.matgyeojo.repository.PetsitterProfileRepo;
import org.matgyeojo.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PetSitterService {
	@Autowired
	S3Uploader s3uploader;
	@Autowired
	UsersRepo userrepo;
	@Autowired
	PetsitterProfileRepo petsitterrepo;
	@Autowired
	PetProfileRepo petrepo;
	@Autowired
	DolbomRepo dolbomrepo;

	// 펫시터 프로필 생성
	public String petSitterInsert(String userId, MultipartFile[] sitterHouse, String sitterHousetype, String sitterMsg)
			throws IOException {
		Users user = userrepo.findById(userId).orElse(null);

		String image_list = "[";

		for (MultipartFile img : sitterHouse) {
			if (img != null && !img.isEmpty()) {
				String sotredFileName = s3uploader.upload(img, "house");
				image_list += (sotredFileName + ",");
			}
		}

		// 마지막에 , 빼주기
		image_list = image_list.substring(0, image_list.length() - 1);
		// 펫시터 프로필 생성
		PetsitterProfile sitter = PetsitterProfile.builder().users(user).sitterHouse(image_list + "]")
				.sitterHousetype(sitterHousetype).sitterMsg(sitterMsg).sitterTem(39.0).build();

		user.setPetsitterProfile(sitter);
		petsitterrepo.save(sitter);
		userrepo.save(user);// 유저 밑에 시터가 있기때문에 유저를 저장

		return user.getUserId();
	}

	// 펫시터 업데이트
	public String petsitterUpdate(String userId, MultipartFile[] sitterHouse, String sitterHousetype, String sitterMsg) throws IOException {
		Users user = userrepo.findById(userId).orElse(null);

		//이미지 리스트시작
		String image_list = "[";

		//배열로 된 파일목록 하나씩 짤라서 저장
		for (MultipartFile img : sitterHouse) {
			if (img != null && !img.isEmpty()) {
				String sotredFileName = s3uploader.upload(img, "house");
				image_list += (sotredFileName + ",");
			}
		}

		// 마지막에 , 빼주기
		image_list = image_list.substring(0, image_list.length() - 1);
		// 펫시터 프로필 업데이트
		PetsitterProfile sitter = petsitterrepo.findByUsers(user);

		sitter.setSitterHouse(image_list + "]");
		sitter.setSitterHousetype(sitterHousetype);
		sitter.setSitterMsg(sitterMsg);

		petsitterrepo.save(sitter);

		return sitter.getUserId();
	}

	// 펫시터프로필 생성
	public String petsitterInsert(String userId, String sitterHousetype, String sitterMsg) {
		String msg = "실패";
		Users user = userrepo.findById(userId).orElse(null);
		PetsitterProfile sitter = petsitterrepo.findById(userId).orElse(null);

		// 업데이트
		if (sitter != null) {
			sitter.setSitterHousetype(sitterHousetype);
			sitter.setSitterMsg(sitterMsg);
			petsitterrepo.save(sitter);
			msg = "펫시터 프로필 업데이트";

			// 생성
		} else {
			PetsitterProfile sitter2 = PetsitterProfile.builder().users(user).sitterHousetype(sitterHousetype)
					.sitterMsg(sitterMsg).sitterTem(39.0).build();

			user.setPetsitterProfile(sitter2);
			userrepo.save(user);// 유저 밑에 시터가 있기때문에 유저를 저장

			msg = "펫시터 프로필 생성";
		}

		System.out.println(msg);
		return msg;
	}
	
	//펫시터 스케쥴 저장
	public int petsitterScadure(String sitterId, String scaduleDay, String scaduleHour, String dolbomOption) {
		Users sitter = userrepo.findById(sitterId).orElse(null);
		
		Dolbom dol = Dolbom.builder().user2(sitter).scheduleDay(scaduleDay).scheduleHour(scaduleHour).dolbomStatus(false).dolbomOption(dolbomOption).build();
		
		dolbomrepo.save(dol);
		return dol.getDolbomNo();
	}

}
