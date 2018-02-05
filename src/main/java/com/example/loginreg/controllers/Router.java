package com.example.loginreg.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/*")//to catch all non-listed routes.
public class Router {
	
	public Router() {
		
	}
	
	@RequestMapping("")
	public String redirect(HttpSession session) {
		if(session.getAttribute("id") !=null) {
			return "redirect:/events";
		}else {
			return "redirect:/register";
		}
	}

}
