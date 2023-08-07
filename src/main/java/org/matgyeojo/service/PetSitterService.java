//package org.matgyeojo.service;
//
//import org.matgyeojo.dto.PetsitterProfile;
//import org.matgyeojo.dto.Users;
//import org.matgyeojo.repository.PetProfileRepo;
//import org.matgyeojo.repository.PetsitterProfileRepo;
//import org.matgyeojo.repository.UsersRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class PetSitterService {
//
//	@Autowired
//	UsersRepo userrepo;
//	@Autowired
//	PetsitterProfileRepo petsitterrepo;
//	@Autowired
//	PetProfileRepo petrepo;
//
//	// 펫시터프로필 생성
//	public String petsitterInsert(String userId, String sitterHousetype, String sitterMsg) {
//		String msg = "실패";
//		Users user = userrepo.findById(userId).orElse(null);
//		PetsitterProfile sitter = petsitterrepo.findById(userId).orElse(null);
//
//		//업데이트
//		if (sitter != null) {
//			sitter.setSitterHousetype(sitterHousetype);
//			sitter.setSitterMsg(sitterMsg);
//			petsitterrepo.save(sitter);
//			msg = "펫시터 프로필 업데이트";
//
//		//생성
//		} else {
//			PetsitterProfile sitter2 = PetsitterProfile.builder().users(user).sitterHousetype(sitterHousetype)
//					.sitterMsg(sitterMsg).sitterTem(39.0).build();
//
//			user.setPetsitterProfile(sitter2);
//			userrepo.save(user);// 유저 밑에 시터가 있기때문에 유저를 저장
//
//			msg = "펫시터 프로필 생성";
//		}
//
//		System.out.println(msg);
//		return msg;
//	}
//
//}
