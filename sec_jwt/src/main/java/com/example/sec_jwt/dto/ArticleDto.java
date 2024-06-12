package com.example.sec_jwt.dto;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.example.sec_jwt.entity.ArticleEntity;
import com.example.sec_jwt.entity.ReporterEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleDto {
	private Integer ano;
	private String title;
	private String content;
	private String writer;
	private MultipartFile file;
	
	public String getFileName() {
		return file.getOriginalFilename();
	}
	
	public ArticleEntity dtoToEntity() {
		ReporterEntity reporter = new ReporterEntity();
		reporter.setUsername(writer);
		String originName = file.getOriginalFilename();
		String newName = UUID.randomUUID().toString() + "_" + originName;
		
		ArticleEntity entity = ArticleEntity.builder()
							.title(title)
							.content(content)
							.reporter(reporter)
							.originName(originName)
							.newName(newName)
							.build();
		
		return entity;
	}
}
