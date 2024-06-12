package com.example.sec_jwt.filter;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.sec_jwt.jwtToken.JWTUtil;
import com.example.sec_jwt.secu.CustomUserDetails;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginFilter extends UsernamePasswordAuthenticationFilter{

	private final AuthenticationManager authenticationManager; //실제 로그인 인증 수행하는 객체 : AuthenticationManager
	private final JWTUtil jwtUtil;
	
	public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		//클라이언트 요청에서 username, password 추출
		String username = obtainUsername(request);
		String password = obtainPassword(request);
		
		//스프링 시큐리티에서 username과 password를 검증하기 위해서는 token에 담아야 함 - super.setAuthenticated(true); // must use super, as we override
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

		//token에 담은 검증을 위한 AuthenticationManager로 전달
        return authenticationManager.authenticate(authToken);
		
	}
	
	//로그인 성공시 실행하는 메소드 (여기서 JWT를 발급하면 됨)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
    	System.out.println("success........... 굳");
    	
    	CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        String username = customUserDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();
        String email = customUserDetails.getEmail();
        															//유효기간
        String token = jwtUtil.createJwt(username, role, email, 60*60*10000L); // 핵심코드 
        
        //응답.응답헤더();
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("writer", username);
        response.addHeader("Role", role);
        response.addHeader("Email", email);
        response.addHeader("Access-Control-Expose-Headers", "Authorization, writer, Role, Email"); //Access-Control-Expose-Headers :클라이언트에서 응답헤더에게 접근가능하게 해주는 코드 
        
    }

	//로그인 실패시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
    	System.out.println("fail...................");
    	response.setStatus(401);
    }
}
