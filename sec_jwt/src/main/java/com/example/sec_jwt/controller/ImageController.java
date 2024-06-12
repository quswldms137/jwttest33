package com.example.sec_jwt.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {

	@GetMapping("/images/{filename}") //Resource 
	public ResponseEntity<Resource> getImage(@PathVariable("filename") String filename){
		try {
			Path file = Paths.get("C:/upload1/" + filename);
			Resource resource = new UrlResource(file.toUri());
			
			if(resource.exists() || resource.isReadable()) {
				HttpHeaders headers = new HttpHeaders();
				headers.add(HttpHeaders.CONTENT_TYPE, Files.probeContentType(file));
				return new ResponseEntity<>(resource, headers, HttpStatus.OK); //상태코드.ok = 200.ok
			}else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND); //NOT_FOUND = error
			}
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
