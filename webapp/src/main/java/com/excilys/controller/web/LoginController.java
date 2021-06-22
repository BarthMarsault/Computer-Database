package com.excilys.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	@GetMapping
	public ModelAndView login() {
		return new ModelAndView();
	}
	
	@PostMapping
	public ModelAndView postLogin() {
		return new ModelAndView();
	}
	
	
}
