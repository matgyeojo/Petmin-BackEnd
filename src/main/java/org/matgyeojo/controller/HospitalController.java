package org.matgyeojo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.http.HttpStatus;
import org.matgyeojo.dto.GyeonggiHospital;
import org.matgyeojo.dto.Hospital;
import org.matgyeojo.dto.Users;
import org.matgyeojo.repository.UsersRepo;
import org.matgyeojo.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hospitals")
public class HospitalController {

    private final HospitalService hospitalService;
    
	@Autowired
	UsersRepo userRepo;

    @Autowired
    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }
    //서울 동뭉병원
//    @GetMapping("/seoul")
//    public void saveDataFromApi() {
//        hospitalService.saveDataFromApi();
//    }
    
    //경기도 동물병원
    @GetMapping("/gyeonggi")
    public void saveDataFromFile() {
        hospitalService.saveDataFromXmlApi();
    }
    
    //지도에 동물병원 정보 전달
    @GetMapping("/map")
    public ResponseEntity<List<Hospital>> getAllHospitals() {
        List<Hospital> hospitals = hospitalService.getAllHospitals();
        return ResponseEntity.ok(hospitals);
    }
    
    //사용자가 주소창에 입력한 텍스트로 병원 검색
    @GetMapping("/maplist")
    public ResponseEntity<Map<String, List<GyeonggiHospital>>> searchHospitals(
            @RequestParam("searchTerm") String searchTerm) {

        List<GyeonggiHospital> hospitals = hospitalService.searchHospitals(searchTerm);

        Map<String, List<GyeonggiHospital>> resultMap = new HashMap<>();
        resultMap.put("hospitals", hospitals);

        return ResponseEntity.ok(resultMap);
    }
    
    // 자기 주소 기반으로 지도 들어갈때 동네 위치 뜨게 하는거 ("동" 기준)
    @GetMapping("/useraddress")
    public ResponseEntity<String> getUserAddressEndingWithDong(@RequestParam String userId) {
       // userId로 유저 정보 조회
       Optional<Users> userOptional = userRepo.findById(userId);

       if (userOptional.isEmpty()) {
          return ResponseEntity.notFound().build();
       }

       Users user = userOptional.get();
       String userAddress = user.getUserAddress();

       // "(" 다음에 오는 동 부분 추출
       int openParenthesisIndex = userAddress.lastIndexOf("(");
       if (openParenthesisIndex == -1) {
          return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body("Dong not found in address");
       }

       int commaIndex = userAddress.lastIndexOf(",");
       if (commaIndex == -1) {
          return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body("Comma not found in address");
       }

       String extractedDong = userAddress.substring(openParenthesisIndex + 1, commaIndex);
       extractedDong = extractedDong.trim();

       return ResponseEntity.ok(extractedDong);
    }
}