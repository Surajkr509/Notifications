package com.example.jwtdemo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.jwtdemo.bean.NotificationsEnum;
import com.example.jwtdemo.config.JwtUtil;
import com.example.jwtdemo.model.JwtRequest;
import com.example.jwtdemo.model.Notifications;
import com.example.jwtdemo.model.Players;
import com.example.jwtdemo.model.Role;
import com.example.jwtdemo.repository.NotificationsRepository;
import com.example.jwtdemo.repository.PlayersRepository;
import com.example.jwtdemo.repository.RoleRepository;
import com.example.jwtdemo.utils.Constants;

@Service
public class PlayersService {

	@Autowired
	PlayersRepository playersRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	JwtUtil jwtUtil;
	@Autowired
	private NotificationsRepository notificationsRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public Object signUp(Players players) {
		Role role = roleRepository.findByRole("PLAYER");
		if (role != null) {
			String password = Constants.getRandomPassword();
			players.setRoleId(role);
			players.setUserName(players.getEmail());
			players.setPassword(bCryptPasswordEncoder.encode(password));
			players.setUpdatedAt(Constants.getDateAndTime());
			players.setCreatedAt(Constants.getDateAndTime());
			playersRepository.save(players);
			Notifications notifications = new Notifications(players.getId(), NotificationsEnum.PLAYER_SIGNUP,
					"A new player has SignUp", Constants.getDateAndTime(), Constants.getDateAndTime());
			
			notificationsRepository.save(notifications);
			HashMap<String, Object> userData = new HashMap<>();
			userData.put("userName", players.getUserName());
			userData.put("password", password);
			return userData;
		} else {
			throw new RuntimeException("Player Role is not exist");
		}
	}

	public boolean checkEmail(String email) {
		Optional<Players> existPlayer = playersRepository.findByEmail(email);
		System.err.println("Email  " + existPlayer);
		return existPlayer.isPresent();
	}

	public boolean checkPassword(String password) {
		Optional<Players> existPlayer = playersRepository.findByPassword(password);
		System.err.println("Password" + existPlayer);
		return existPlayer.isPresent();
	}

	public Object login(JwtRequest jwtRequest) {
		Optional<Players> existData = playersRepository.findByEmail(jwtRequest.getEmail());
		if (existData.isPresent()) {
			Players user = existData.get();
			String jwt = jwtUtil.generateJwtToken(user);
			user.setToken(jwt);
			playersRepository.save(user);
			HashMap<String, Object> userData = new HashMap<>();
			userData.put("email", user.getEmail());
			userData.put("mobNo", user.getMobNo());
			userData.put("token", jwt);
			System.err.println("::Token:::" + jwt);
			return userData;
		} else {
			throw new RuntimeException("Invalid Credentials");
		}

	}

	public Object forgotPassword(Players user) {
		String otp = Constants.generateOTP();
		user.setOtp(otp);
		HashMap<String, Object> userData = new HashMap<>();
		userData.put("playerId", user.getId());
		userData.put("otp", otp);
		playersRepository.save(user);
		return userData;
	}

	public void changePassword(Players user, String newPassword) {
		user.setPassword(bCryptPasswordEncoder.encode(newPassword));
		playersRepository.save(user);
	}

	/*
	 * public Object getAllNotifications(Authentication auth) {
	 * System.err.println("Player"+auth);
	 * 
	 * Long playerId=playersRepository.findIdByEmail(auth.getName());
	 * List<Notifications>
	 * notifications=notificationsRepository.findAllByPlayerId(playerId);
	 * List<Object> dataList=new ArrayList<>(); if(!notifications.isEmpty()) {
	 * for(Notifications notification:notifications) { HashMap<String, Object>
	 * userData=new HashMap<>(); userData.put("playerId", notification.getId());
	 * userData.put("message",notification.getMessage()); dataList.add(userData); }
	 * } return dataList;
	 * 
	 * }
	 */
	public List<Players> getAllPlayers(){
		return playersRepository.findAll();
	}
}
