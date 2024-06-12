package com.example.sec_jwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.sec_jwt.dto.ReporterDto;
import com.example.sec_jwt.entity.ReporterEntity;
import com.example.sec_jwt.repository.ReporterRepository;

@Service
public class JoinService {
	
	@Autowired
	private ReporterRepository reporterRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public void registReporter(ReporterDto reporterDto) {
		
		ReporterEntity reporter = reporterDto.dtoToEntity();
		
		//Password 암호화
		String newPw = bCryptPasswordEncoder.encode(reporter.getPassword());
		
		//Role 부여
		String role = "";
		if(reporter.getUsername().equals("manager")) {
			role = "ROLE_MANAGER";
		}else {
			role = "ROLE_REPORTER";
		}
		reporter.setRole(role);
		
		reporterRepository.save(null);
	}
}
