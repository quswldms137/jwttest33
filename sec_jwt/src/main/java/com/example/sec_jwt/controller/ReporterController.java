package com.example.sec_jwt.controller;

import java.io.File;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sec_jwt.dto.ArticleDto;
import com.example.sec_jwt.entity.ArticleEntity;
import com.example.sec_jwt.repository.ArticleRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.coobird.thumbnailator.Thumbnails;

//@CrossOrigin("*")
@RestController
@RequestMapping("/reporter")
public class ReporterController {

	@Autowired
	private ArticleRepository articleRepository;
	
	@Value("${spring.servlet.multipart.location}")
	private String uploadPath;
	
	@GetMapping("/article")
	public ResponseEntity<List<ArticleEntity>> getAllArticles() {
        List<ArticleEntity> articles = articleRepository.findAll();
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }
	
	@GetMapping("/article/{ano}")
	public ResponseEntity<ArticleEntity> getArticle(@PathVariable("ano") Integer ano, HttpServletRequest request, HttpServletResponse response) {
		System.out.println("getArticle(/reporter/article/1)" + ", " + ano);
		
		Enumeration<String> headers = request.getHeaderNames();
		while(headers.hasMoreElements()) {
			//System.out.println(headers.nextElement());
			if(headers.nextElement().equals("writer")) {
				System.out.println(request.getHeader("writer"));
			}
		}
		response.setHeader("writer", "aaa");
		response.addHeader("Access-Control-Expose-Headers", "writer");
        Optional<ArticleEntity> article = articleRepository.findById(ano);
        if(article.isPresent()) {
        	return new ResponseEntity<>(article.get(), HttpStatus.OK);
        }else {
        	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	
	@PostMapping("/article")
	public String postArticle(ArticleDto articleDto) {
		System.out.println("post article : " + articleDto);
		
		String originName = articleDto.getFileName();
		String newName = UUID.randomUUID().toString() + "_" + originName;
		ArticleEntity articleEntity = articleDto.dtoToEntity();
		
		// 파일 저장
		File file = new File(newName);
		
		try {
			articleDto.getFile().transferTo(file);
			System.out.println("파일 업로드 성공...");
			
			// 썸네일 생성
			String thumbnailSaveName = "s_" + newName;
			articleEntity.setThumbnailName(thumbnailSaveName);

			File thumbnailFile = new File(uploadPath + thumbnailSaveName);
			File ufile = new File(uploadPath + newName);
			// Thumbnailator.createThumbnail(file, thumbnailFile, 100, 100);
			Thumbnails.of(ufile).size(100, 100).toFile(thumbnailFile);

		}catch(Exception e) {
			e.printStackTrace();
		}
		
		ArticleEntity result = articleRepository.save(articleEntity);
		
		if(result != null) {
			return "success writer article....";
		}
		
		return "fail....";
	}
}
