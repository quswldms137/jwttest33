package com.example.sec_jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sec_jwt.entity.ReporterEntity;

public interface ReporterRepository extends JpaRepository<ReporterEntity, String>{

	public ReporterEntity findByUsername(String username);

}
