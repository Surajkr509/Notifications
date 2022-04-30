package com.example.jwtdemo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.jwtdemo.model.Notifications;
import com.example.jwtdemo.repository.NotificationsRepository;

@Service

public class NotificationsService {
	
	@Autowired
	NotificationsRepository notificationsRepository;
	
	
	public Object updateNotificationsAsReadStatus(String typeId) {
		Notifications notifications=notificationsRepository.findByTypeId(typeId);
		if(notifications!=null) {
			notifications.setReadStatus(1);
			notificationsRepository.save(notifications);
		} return null;
	}
	
	public Object getAllUnReadNotifications(HttpServletRequest http) {
		List<Notifications> notifications=notificationsRepository.findAllByReadStatus(0);
		List<Object> listdata=new ArrayList<>();
		for(Notifications notification:notifications) {
			HashMap<String, Object> hashMap=new HashMap<>();
			hashMap.put("id",notification.getId());
			hashMap.put("type",notification.getType());
			hashMap.put("typeId",notification.getTypeId());
			hashMap.put("readStatus",notification.getReadStatus());
			hashMap.put("message",notification.getMessage());
			hashMap.put("createdAt",notification.getCreatedAt());
			listdata.add(hashMap);
		} return listdata;
		
	}
	public Object getAllReadNotifications(HttpServletRequest http) {
		List<Notifications> notifications=notificationsRepository.findAllByReadStatus(1);
		List<Object> listdata=new ArrayList<>();
		for(Notifications notification:notifications) {
			HashMap<String, Object> hashMap=new HashMap<>();
			hashMap.put("id",notification.getId());
			hashMap.put("type",notification.getType());
			hashMap.put("typeId",notification.getTypeId());
			hashMap.put("readStatus",notification.getReadStatus());
			hashMap.put("message",notification.getMessage());
			hashMap.put("createdAt",notification.getCreatedAt());
			listdata.add(hashMap);
		} return listdata;
		
	}
	public Long countAllUnReadNotifications() {
		return notificationsRepository.countAllByReadStatus(0);
	}
	
	public List<Notifications> getAllNotifications() {
		return notificationsRepository.findAllNotifications();
	}

}
