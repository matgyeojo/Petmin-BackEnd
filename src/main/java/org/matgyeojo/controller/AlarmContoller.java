package org.matgyeojo.controller;

import java.util.List;

import org.matgyeojo.repository.AlarmRepo;
import org.matgyeojo.repository.UsersRepo;
import org.matgyeojo.service.AlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alarm")
public class AlarmContoller {
	@Autowired
	AlarmRepo alrepo;
	@Autowired
	UsersRepo userrepo;
	@Autowired
	AlarmService alarmservice;
	
	@GetMapping(value = "/search")
	public List<String> alarmSearch(String userId) {
		return alarmservice.alarmSearch(userId);
	}
	@GetMapping(value = "/search2")
	public List<String> alarmSearchMain(String userId) {
		return alarmservice.alarmSearchMain(userId);
	}
	
}
