package com.example.sec_jwt.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sec_jwt.entity.ArticleEntity;
import com.example.sec_jwt.repository.ArticleRepository;

//@CrossOrigin("*")
@RestController
@RequestMapping("/api/articles")
public class ArticleController {
	
	@Autowired
	private ArticleRepository articleRepository;
	
	@GetMapping
	public ResponseEntity<List<ArticleEntity>> getAllArticles() {
        List<ArticleEntity> articles = articleRepository.findAll();
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }
	
	@GetMapping("/{ano}")
	public ResponseEntity<ArticleEntity> getArticleById(@PathVariable("ano") Integer ano) {
		System.out.println("article api get................" + ano);
        Optional<ArticleEntity> article = articleRepository.findById(ano);
        if (article.isPresent()) {
            return new ResponseEntity<>(article.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
