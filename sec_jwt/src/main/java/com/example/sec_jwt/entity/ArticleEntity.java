package com.example.sec_jwt.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "articles")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleEntity extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer ano;
	
	private String title;
	private String content;
	private String originName;
	private String newName;
	private String thumbnailName;
	
	@ManyToOne
	@JoinColumn(name = "writer")
	private ReporterEntity reporter;
	
}
