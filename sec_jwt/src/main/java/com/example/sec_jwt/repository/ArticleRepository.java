package com.example.sec_jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sec_jwt.entity.ArticleEntity;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Integer>{

}
