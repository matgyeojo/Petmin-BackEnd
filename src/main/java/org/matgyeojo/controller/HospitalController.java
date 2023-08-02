package org.matgyeojo.controller;

import org.matgyeojo.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hospitals")
public class HospitalController {

    private final HospitalService hospitalService;

    @Autowired
    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }
    //서울 동뭉병원 - 이슈있음
    @GetMapping("/seoul")
    public void saveDataFromApi() {
        hospitalService.saveDataFromApi();
    }
    
    //경기도 동물병원
    @GetMapping("/gyeonggi")
    public void saveDataFromFile() {
        hospitalService.saveDataFromXmlApi();
    }
}