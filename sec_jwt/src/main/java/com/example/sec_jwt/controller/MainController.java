package com.example.sec_jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sec_jwt.dto.ReporterDto;
import com.example.sec_jwt.entity.ReporterEntity;
import com.example.sec_jwt.repository.ReporterRepository;

//@CrossOrigin("*")
@RestController
public class MainController {

	@Autowired
	private ReporterRepository reporterRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder; //암호화 해주는 객체 
	
	@GetMapping("/")
	public String root() {
		System.out.println("root..........");
		return "root.......";
	}
	
	@PostMapping("/join") //회원가입  
	public String join(ReporterDto reporterDto) {
		System.out.println("param : " + reporterDto);
		String newPw = bCryptPasswordEncoder.encode(reporterDto.getPassword()); //newPw : 암호화된 새 비밀번호 
		String role = "";
		if(reporterDto.getUsername().equals("manager")) {
			role = "ROLE_MANAGER";
		}else {
			role = "ROLE_REPORTER";
		}
		ReporterEntity reporterEntity = reporterDto.dtoToEntity();
		
		reporterEntity.setPassword(newPw);
		reporterEntity.setRole(role);
		
		ReporterEntity result = reporterRepository.save(reporterEntity);
		
		if(result != null) {
			return "success";
		}
		
		return "fail!";
	}
	
	@GetMapping("/images")
	public String showImg() {
		System.out.println("xxxxxxxxxx.....");
		return "img";
	}
}
