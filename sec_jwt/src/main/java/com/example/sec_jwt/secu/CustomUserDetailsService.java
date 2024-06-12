package com.example.sec_jwt.secu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.sec_jwt.entity.ReporterEntity;
import com.example.sec_jwt.repository.ReporterRepository;

@Service										//구현체
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private ReporterRepository reporterRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		ReporterEntity reporter = reporterRepository.findByUsername(username); 
		
		System.out.println("reporterEntity : " + reporter);
		
		if(reporter != null) {
			CustomUserDetails userData = new CustomUserDetails(reporter);
			return userData;
		}
		
		return null;
	}

	
}
