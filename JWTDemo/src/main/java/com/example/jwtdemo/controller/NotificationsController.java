package com.example.jwtdemo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.jwtdemo.bean.ResultDTO;
import com.example.jwtdemo.model.Notifications;
import com.example.jwtdemo.service.NotificationsService;
import com.example.jwtdemo.service.PlayersService;

@Controller
@RequestMapping
public class NotificationsController {
	
	@Autowired
	NotificationsService notificationsService;
	
//	@GetMapping("/getAllNotifications")
//	public ResponseEntity<?> getAllNotifications(Authentication auth){
//		System.out.println(":::::GetAllNotification::AuthController::::");
//		ResultDTO<?> responsePacket=null;
//		try {
//			responsePacket=new ResultDTO<>(true,playersService.getAllNotifications(auth),"All records fetched successfully");
//			return new ResponseEntity<>(responsePacket,HttpStatus.OK);
//		} catch(Exception e) {
//			e.printStackTrace();
//			responsePacket = new ResultDTO<>(false,e.getMessage());
//			return new ResponseEntity<>(responsePacket,HttpStatus.BAD_REQUEST);
//		}
//	}
	
	@GetMapping(value = "/getAllUnReadNotificationsCount")
	@ResponseBody
	public Long getAllUnReadNotificationsCount(Model model) {
	 Long count=notificationsService.countAllUnReadNotifications();
	 model.addAttribute("count",count);
	 return null;
	}
	
	@GetMapping(value = "/getAllUnReadNotifications")
	@ResponseBody
	public Object getAllUnReadNotifications() {
		return notificationsService.getAllUnReadNotifications();
	}

	@GetMapping(value = "/getAllNotifications")
	@ResponseBody
	public Object getAllNotifications() {
		return notificationsService.getAllNotifications();
	}
	@GetMapping(value="/getAllReadNotifications")
	@ResponseBody
	public Object getAllReadNotification(HttpServletRequest http) {
		return notificationsService.getAllReadNotifications(http);
	}
	@GetMapping("/home")
	public String getAllNotifications(Model model) {
		 Long count=notificationsService.countAllUnReadNotifications();
		 Object notifications=notificationsService.getAllUnReadNotifications();
		 model.addAttribute("count",count);
		 model.addAttribute("noticelist", notifications);
		 return "index";
	}
}
