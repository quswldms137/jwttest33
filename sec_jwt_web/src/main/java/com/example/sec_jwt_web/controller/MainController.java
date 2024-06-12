package com.example.sec_jwt_web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	
	@RequestMapping("/writeArticle")
	public String writeArticle() {
		System.out.println("writeArticle.......");
		return "writeArticle";
	}
}
