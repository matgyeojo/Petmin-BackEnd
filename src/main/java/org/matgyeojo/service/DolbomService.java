package org.matgyeojo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.matgyeojo.dto.Dolbom;
import org.matgyeojo.dto.PetProfile;
import org.matgyeojo.dto.PetsitterProfile;
import org.matgyeojo.dto.Preference;
import org.matgyeojo.dto.Review;
import org.matgyeojo.dto.Users;
import org.matgyeojo.repository.DolbomRepo;
import org.matgyeojo.repository.PetProfileRepo;
import org.matgyeojo.repository.PetsitterProfileRepo;
import org.matgyeojo.repository.PreferenceRepo;
import org.matgyeojo.repository.ReviewRepo;
import org.matgyeojo.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.java.Log;


@Service
public class DolbomService {
	@Autowired
	UsersRepo userrepo;
	@Autowired
	PetsitterProfileRepo petsitterrepo;
	@Autowired
	PetProfileRepo petrepo;
	@Autowired
	DolbomRepo dolbomrepo;
	@Autowired
	PreferenceRepo prerepo;
	@Autowired
	ReviewRepo reviewrepo;

	public List<Object> dolbomFilter(String userId,String userAddress){
		Users u = userrepo.findById(userId).orElse(null);
		Preference pre =  prerepo.findByUser(u);
		//String userSex,int userAge,String sitterHousetype, String petSex,double petWeight,String userAddress
		//선호도 저장
		String userSex = pre.getPreference1();
		int userAge = pre.getPreference2();
		String sitterHousetype = pre.getPreference3();
		String petSex = pre.getPreference4();
		String petWeight =pre.getPreference5();
		if(userAddress.equals("")) {
			userAddress = u.getUserAddress();
		}
		
		//1.users 테이블 = 성별, 나이
		//2.sitter테이블 = 집 타입
		//3.펫 프로필 테이블 = 성별 , 몸무게 (소형견 : 10kg미만,중형견:10~25,대형견 25~)
		List<Users> users = userrepo.findByUserSexAndUserAgeGreaterThanEqualAndUserAddressStartingWith(userSex, userAge,userAddress);
		List<PetsitterProfile> sitters = petsitterrepo.findBySitterHousetypeOrderBySitterUpdateDesc(sitterHousetype);
		//if로 몸무게 조건걸어서 소중대형견 판별 지금은 예시로 소형견만
		List <PetProfile> pets = new ArrayList<PetProfile>();
		if(petWeight.equals("소형견")) {
			pets = petrepo.findByPetSexAndPetWeightLessThan(petSex, 10.00);
		}else if(petWeight.equals("중형견")) {
			pets = petrepo.findByPetSexAndPetWeightBetween(petSex, 10.00, 25.00);
		}else {
			pets= petrepo.findByPetSexAndPetWeightGreaterThan(petSex,25.00);
		}
		
		List<String> filter1 = new ArrayList<>();
		users.forEach(user->{
			filter1.add(user.getUserId());
		});
		
		List<String> filter2 = new ArrayList<String>();
		sitters.forEach(sitter->{
			filter2.add(sitter.getUserId());		
		});
		
		List<String> filter3 = new ArrayList<String>();
		pets.forEach(pet->{
			filter3.add(pet.getUser().getUserId());
			
		});
		List<String> filter4= new ArrayList<String>();
		for(String fil1 : filter1) {//필터링
			for(String fil2 : filter2) {
				for(String fil3 : filter3) {
					if(fil1.equals(fil2) && fil2.equals(fil3) && fil3.equals(fil1)) {
						filter4.add(fil1);
					}
				}
			}
		}
		
		//List에 map을 저장
		List<Object> result = new ArrayList<>();
		
		for(String fil : filter4) {
			
			HashMap<String, Object> map = new HashMap<>();
			Users user = userrepo.findById(fil).orElse(null);
			PetsitterProfile sitter = petsitterrepo.findById(fil).orElse(null);
			
			//프론트에서 필터링하는데 필요한 부분
			List<Dolbom> dol = dolbomrepo.findByUser2(user);
			String day="";
			String option="";
			for(Dolbom d : dol) {
			day = day+d.getScheduleDay()+",";
			option = option+d.getDolbomOption()+",";	
			}
			if(day.length()>2) {// 쉼표 없애주기
				day = day.substring(0,day.length()-1);
				option = option.substring(0,option.length()-1);
			}
			map.put("userName",user.getUserName());
			map.put("userAddress",user.getUserAddress());
			map.put("sitterHouse", sitter.getSitterHouse());
			map.put("sitterMsg", sitter.getSitterMsg());
			map.put("sitterTem", sitter.getSitterTem());
			map.put("scheduleDay",day);
			map.put("dolbomOption", option);
			result.add(map);
		}
		
		return result;
	}

	//펫시터 자세히보기
	public 	List<Object> dolbomDetail(String sitterId) {
		//유저 - userImg,userAddress,userName,userLicence,userAge,userSex
		//펫시터 - sitterHouse,sitterHousetype,sitterMsg,sitterTem,
		//펫 -petName,petSex,petMsg,petAge,petNo
		//리뷰-petsitter,user,reviewMsg
		Users user = userrepo.findById(sitterId).orElse(null);
		PetsitterProfile sitter = petsitterrepo.findByUsers(user);
		List<PetProfile> pets = petrepo.findByUser(user);
		List<Review> reviews = reviewrepo.findByPetsitter(user);
		List<Dolbom> dolboms = dolbomrepo.findByUser2(user);
		
		//리뷰 평균내기
		double time= 0;
		double kind= 0;
		double delecacy = 0;
		double allReview = 0;
		int count =0;
		for(Review re : reviews) {
			time+=re.getReviewTime();
			kind+=re.getReviewKind();
			delecacy+=re.getReviewDelecacy();
			count++;
		}
		time/=count;kind/=count;delecacy/=count;
		allReview = (time+kind+delecacy)/3;
		
		List<Object> dolList = new ArrayList<>(); 
		
		HashMap<String, Object> map = new HashMap<String,Object>() ;
		map.put("userImg", user.getUserImg());
		map.put("petsitter",sitter);
		map.put("pet", pets);
		map.put("reviewTime", time);
		map.put("reviewKind", kind);
		map.put("reviewDelecacy", delecacy);
		map.put("reviewAll", allReview);
		
		
		dolList.add(map);
		
		return dolList;
	}
}
