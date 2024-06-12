package com.example.sec_jwt.jwtToken;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;

@Component
public class JWTUtil {
	
	private SecretKey secretKey;
	
	public JWTUtil(@Value("${spring.jwt.secret}")String secret) {
		this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
		System.out.println("secretKey : " + secretKey.toString() + ", algorithm : " + secretKey.getAlgorithm());
	}
	
	// 토큰에서 사용자 정보 얻어내기 (Username, Expired)
	public String getUsername(String token) {
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
	}
	
	public String getRole(String token) {
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role", String.class);
	}
	
	public String getEmail(String token) {
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("email", String.class);
	}
	
	public Boolean isExpired(String token) {
		return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
	}
	
	//Token 생성
	public String createJwt(String username, String role, String email, Long expiredMs) {
		
		return Jwts.builder()
						.claim("username", username)
						.claim("role", role)
						.claim("email", email)
						.issuedAt(new Date(System.currentTimeMillis())) // 토큰 발행 시점.
						.expiration(new Date(System.currentTimeMillis()+ expiredMs))//LoginFilter에서 설정한 유효시간을 현재 시간에 더함.
						.signWith(secretKey)
						.compact();
	}
	
	
}
