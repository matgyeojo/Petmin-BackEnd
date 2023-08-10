package org.matgyeojo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.matgyeojo.dto.Alarm;
import org.matgyeojo.dto.Users;
import org.matgyeojo.repository.AlarmRepo;
import org.matgyeojo.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service 
public class AlarmService {
	@Autowired
	AlarmRepo arrepo;
	@Autowired
	UsersRepo userrepo;
	
	public List<String> alarmSearch(String userId) {
		Users user = userrepo.findById(userId).orElse(null);
		List<Alarm> alarm =  arrepo.findByUser(user);
		List<String> almsg = new ArrayList<String>();
		
		Date now = new Date();
		
		for(Alarm al : alarm) {
			Date alTime = al.getAlarmDate();
		    long diffInMillies = now.getTime()-alTime.getTime() ;
		    long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillies);
			
			almsg.add(al.getAlarmMsg()+diffInMinutes+":"+al.getAlarmState());
			al.setAlarmState(true);
			arrepo.save(al);
		}
		
		
		return almsg;
	}
	public List<String> alarmSearchMain(String userId) {
		Users user = userrepo.findById(userId).orElse(null);
		List<Alarm> alarm =  arrepo.findByUser(user);
		List<String> almsg = new ArrayList<String>();
		
		Date now = new Date();
		
		for(Alarm al : alarm) {
			Date alTime = al.getAlarmDate();
		    long diffInMillies = now.getTime()-alTime.getTime() ;
		    long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillies);
			
			almsg.add(al.getAlarmMsg()+diffInMinutes+":"+al.getAlarmState());
		
		}
		
		
		return almsg;
	}

	
}
