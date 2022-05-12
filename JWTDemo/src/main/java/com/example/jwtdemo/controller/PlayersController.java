package com.example.jwtdemo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.jwtdemo.model.Players;
import com.example.jwtdemo.service.PlayersService;

@Controller
@RequestMapping("/admin")
public class PlayersController {
 
	@Autowired
	PlayersService playersService;
	
	
	@GetMapping("/viewPlayers")
	public String viewPlayers(Model modelAndView) {
		System.err.println(":::PlayersController.viewPLayers:::");
		List<Players> playerdata=playersService.getAllPlayers();
		if(!playerdata.isEmpty()) {
			modelAndView.addAttribute("playerslist", playerdata);
			return "players/playersList";
		} else {
			return "index";
		}
	}
	
}
