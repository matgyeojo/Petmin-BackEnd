package org.matgyeojo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.matgyeojo.dto.Dolbom;
import org.matgyeojo.dto.PetsitterProfile;
import org.matgyeojo.dto.Users;
import org.matgyeojo.repository.DolbomRepo;
import org.matgyeojo.repository.PetProfileRepo;
import org.matgyeojo.repository.PetsitterProfileRepo;
import org.matgyeojo.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.java.Log;

@Service
@Log
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
	public String petsitterUpdate(String userId, MultipartFile[] sitterHouse, String sitterHousetype, String sitterMsg)
			throws IOException {
		Users user = userrepo.findById(userId).orElse(null);

		// 이미지 리스트시작
		String image_list = "[";

		// 배열로 된 파일목록 하나씩 짤라서 저장
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

	// 펫시터프로필 이미지 없을 때
	public String petsitterUpdate2(String userId, String house, String sitterHousetype, String sitterMsg) {
		Users user = userrepo.findById(userId).orElse(null);
		// 펫시터 프로필 업데이트
		PetsitterProfile sitter = petsitterrepo.findByUsers(user);

		String houses = house.substring(0, house.length());
		String[] ho = houses.split(",");

		String newhouse = "[";
		for (String h : ho) {
			newhouse += h + ",";
		}
		newhouse = newhouse.substring(0, newhouse.length() - 1);
		newhouse += "]";

		sitter.setSitterHouse(newhouse);
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

	// 펫시터 스케쥴 저장
	public int petsitterScadure(String sitterId, String scheduleDay, String[] scheduleHour, String dolbomOption) {
		int msg = 0;
		Users sitter = userrepo.findById(sitterId).orElse(null);

		// 그러면 처음 만드는 거 이거나 있는데 값이 안들어 온 것.
		// 그러면 삭제
		// 그러면 option값만 변경되며능ㄴ?
		for (String s : scheduleHour) {
			List<Dolbom> dols = dolbomrepo.findByUser2AndScheduleDay(sitter, scheduleDay);

			for (Dolbom dolbom : dols) {
				// 리스트에서 모든 애들중에 돌봄예약 안된애들은 그냥 삭제
				if (dolbom.getDolbomStatus().equals(false)) {
					dolbomrepo.delete(dolbom);
				}
				// 삭제 안된 애들중에 돌봄 옵션이 일치하지 않으면
				if (!dolbom.getDolbomOption().equals(dolbomOption) && dolbom.getDolbomStatus().equals(false)) {
					dolbom.setDolbomOption(dolbomOption);
					dolbomrepo.save(dolbom);
				}
			}
		}

		// 가능한 시간을 배열로 입력받아 따로따로 저장
		for (String s : scheduleHour) {
			if (dolbomrepo.findByUser2AndScheduleDayAndScheduleHour(sitterId, scheduleDay, s) > 0) {
				continue;
			}

			Dolbom dol = Dolbom.builder().user2(sitter).scheduleDay(scheduleDay).scheduleHour(s).dolbomStatus(false)
					.dolbomOption(dolbomOption).build();
			dolbomrepo.save(dol);
		}
		List<Dolbom> dols = dolbomrepo.findByScheduleDay(scheduleDay);
		int count = 0;
		for (Dolbom dol : dols) {
			count++;
		}
		if (count > 0) {
			msg = 1;
		}

		return msg;
	}

	// 펫시터 프로필 가져오기
	public PetsitterProfile getSitter(String userId) {
		PetsitterProfile sitter = petsitterrepo.findById(userId).orElse(null);
		return sitter;
	}

	// 펫시터 일정 가져오기
	public List<Object> getSchedure(String sitterId, String scheduleDay) {
		Users user = userrepo.findById(sitterId).orElse(null);
		List<Dolbom> dols = dolbomrepo.findByUser2AndScheduleDay(user, scheduleDay);
		List<Object> result = new ArrayList<>();

		for (Dolbom dol : dols) {
			HashMap<String, Object> map = new HashMap<>();
			map.put("day", scheduleDay);
			map.put("dolbomOption", dol.getDolbomOption());

			HashMap<String, Object> map2 = new HashMap<>();
			map2.put("Hour2", dol.getScheduleHour());
			map2.put("dolbomStatus", dol.getDolbomStatus());
			map.put("Hour", map2);
			result.add(map);
		}

		return result;
	}

}
