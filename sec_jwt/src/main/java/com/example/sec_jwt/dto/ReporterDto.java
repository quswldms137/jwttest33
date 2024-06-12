package com.example.sec_jwt.dto;

import com.example.sec_jwt.entity.ReporterEntity;

import lombok.Data;

@Data
public class ReporterDto {
	private String username;
	private String password;
	private String name;
	private String email;
	private String role;
	
	public ReporterEntity dtoToEntity() {
		ReporterEntity entity = ReporterEntity.builder()
									.username(username)
									.password(password)
									.name(name)
									.email(email)
									.role(role)
									.build();
		return entity;
	}
}
