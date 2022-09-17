package com.example.jwtdemo.controller;


import java.util.ArrayList;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.jwtdemo.bean.ChangePassword;
import com.example.jwtdemo.bean.ResultDTO;
import com.example.jwtdemo.model.JwtRequest;
import com.example.jwtdemo.model.Notifications;
import com.example.jwtdemo.model.Players;
import com.example.jwtdemo.repository.PlayersRepository;
import com.example.jwtdemo.service.NotificationsService;
import com.example.jwtdemo.service.PlayersService;
import com.example.jwtdemo.utils.BeanValidator;
import com.example.jwtdemo.utils.Constants;

@Controller
@RequestMapping("/auth")

public class AuthController {

	@Autowired
	PlayersService playersService;

	@Autowired
	PlayersRepository playersRepository;

	@Autowired
	BeanValidator bean;
	
	@Autowired
	NotificationsService notificationsService;

	@PostMapping("/addPlayer")
	public ModelAndView signUp(@Valid Players players) {
		System.err.println("SignUp::Controller");
		ModelAndView model=new ModelAndView();
		ResultDTO<?> responsePacket=null;
		try {
			ArrayList<String> errorList=bean.signUpValidation(players);
			if(errorList.size()!=0) {
			ResultDTO<ArrayList<String>> errorPacket=new ResultDTO<>(false,errorList,Constants.invalidData);
			model.addObject("errors",errorPacket);
			model.setViewName("/signUp");
			return model;
		}
			if(playersRepository.existsByEmail(players.getEmail()) || playersRepository.existsByMobNo(players.getMobNo())) {
				String msg ="Player Already Exists by email or mobile no.";
				model.addObject("response",msg);
				model.setViewName("/signUp");
				return model;
			} else {
				responsePacket=new ResultDTO<>(true,playersService.signUp(players),Constants.requestSuccess);
				model.addObject(responsePacket);
				model.setViewName("/login");
				return model;
			}
			}catch (Exception e) {
				e.printStackTrace();
				responsePacket=new ResultDTO<>(false,null,e.getMessage());
				model.addObject(responsePacket);
				model.setViewName("/signUp");
				return model;
			}
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login (@Valid @ModelAttribute JwtRequest jwtRequest,BindingResult bindingResult){
		System.err.println("::Login AuthController");
		ResultDTO<?> responsePacket=null;
		try {
			if(jwtRequest.getEmail().equals("") || jwtRequest.getPassword().equals("")) {
				responsePacket=new ResultDTO<>(false,null,"*Please enter correct details");
				return new ResponseEntity<>(responsePacket,HttpStatus.BAD_REQUEST);
				
			}
				
			if(playersService.checkEmail(jwtRequest.getEmail())==false) {
//				bindingResult.rejectValue("email","errors.jwtRequest.email", "Pls provide correct Email");
				responsePacket=new ResultDTO<>(false,null,"Enter email does not exist");
				return new ResponseEntity<>(responsePacket,HttpStatus.BAD_REQUEST);
			}
			Players pass =playersRepository.findByUserName(jwtRequest.getEmail());	
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			if(encoder.matches(jwtRequest.getPassword(), pass.getPassword())==false) {
//				bindingResult.rejectValue("password","errors.jwtRequest.password","Pls provide correct password");
				responsePacket=new ResultDTO<>(false,null,"Enter correct password");
				return new ResponseEntity<>(responsePacket,HttpStatus.BAD_REQUEST);
			
			}
			return ResponseEntity.ok(playersService.login(jwtRequest));
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		}
	@PostMapping("/forgotPassword/{userName}")
	public Object forgotPassword(@PathVariable ("userName") String userName) {
		System.out.println("ForgotPassword::::Controller");
		ResultDTO<?> responsePacket=null;
		try {
			Players user=playersRepository.findByUserName(userName);
			if(user!=null) {
				responsePacket=new ResultDTO<>(true,playersService.forgotPassword(user),"OTP sent successfully");
				return new ResponseEntity<>(responsePacket,HttpStatus.OK);
			} else {
				responsePacket=new ResultDTO<>(false,"Player not exists");
				return new ResponseEntity<>(responsePacket,HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			responsePacket=new ResultDTO<>(false,e.getMessage());
			return new ResponseEntity<>(responsePacket,HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/changePassword")
	public Object changePassword(@RequestBody ChangePassword data) {
		System.out.println(":::ChangePassword::AuthController:::");
		ResultDTO<?> responsePacket=null;
		try {
			Players user=playersRepository.findById(data.getPlayerId()).orElse(null);
			if(user!=null) {
				if(data.getOtp().equals(user.getOtp())) {
					if(data.getNewPassword().equals(data.getConfirmPassword())) {
						playersService.changePassword(user,data.getNewPassword());
					} else {
						responsePacket=new ResultDTO<>(false,"NewPassword & Confirm password must be same");
						return new ResponseEntity<>(responsePacket,HttpStatus.BAD_REQUEST);
					}
				}else {
					responsePacket=new ResultDTO<>(false,"Enter valid OTP");
					return new ResponseEntity<>(responsePacket,HttpStatus.BAD_REQUEST);
				}
			}else {
				responsePacket=new ResultDTO<>(false,"Player not exists");
				return new ResponseEntity<>(responsePacket,HttpStatus.BAD_REQUEST);
			}
			responsePacket=new ResultDTO<>(true,"Password changed Successfully");
			return new ResponseEntity<>(responsePacket,HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			responsePacket=new ResultDTO<>(false,e.getMessage());
			return new ResponseEntity<>(responsePacket,HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/signUp")
	public String signIn(Players players) {
		return"signUp";
	}
	
	@GetMapping("/login")
	public String login() {
		return"login";
	}
}
