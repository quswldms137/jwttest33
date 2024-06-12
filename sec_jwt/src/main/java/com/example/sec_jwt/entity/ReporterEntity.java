package com.example.sec_jwt.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reporter")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReporterEntity {
	
	@Id
	private String username;
	@Column(nullable = false)
	private String password;
	private String name;
	@Column(unique = true)
	private String email;
	@Column(nullable = false)
	private String role;
	
}
