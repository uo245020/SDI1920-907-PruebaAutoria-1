package com.uniovi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@RequestMapping("/")
	public String goHome(){
		return "home";
	}
	
	@RequestMapping("/admin")
	public String admin(){
		return "admin";
	}
}
