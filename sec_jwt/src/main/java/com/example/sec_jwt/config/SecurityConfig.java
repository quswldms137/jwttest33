package com.example.sec_jwt.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.example.sec_jwt.filter.LoginFilter;
import com.example.sec_jwt.jwtToken.JWTFilter;
import com.example.sec_jwt.jwtToken.JWTUtil;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final AuthenticationConfiguration authenticationConfiguration;
	//JWTUtil 주입
	private final JWTUtil jwtUtil;
	
	public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JWTUtil jwtUtil) {

        this.authenticationConfiguration = authenticationConfiguration;
		this.jwtUtil = jwtUtil;
    }
	
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }
		   //시큐리티 사용하기 위해 
	@Bean  //암호화 : BCryptPasswordEncoder
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean  //filterChain                              //http 통해 인가 설정 
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		/*
		http
        .cors((corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {

            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                CorsConfiguration configuration = new CorsConfiguration();

                configuration.setAllowedOrigins(Collections.singletonList("http://localhost:9001/"));
                configuration.setAllowedMethods(Collections.singletonList("*"));
                configuration.setAllowCredentials(true);
                configuration.setAllowedHeaders(Collections.singletonList("*"));
                configuration.setMaxAge(36000000L);

				configuration.setExposedHeaders(Collections.singletonList("Authorization"));

                return configuration;
            }
        })));
		
		*/
		//csrf 비활성
		http.csrf(auth -> auth.disable());
		//basic 로그인 비활성
		http.httpBasic(auth -> auth.disable());
		//form 로그인 비활성
		http.formLogin(auth -> auth.disable());
		
		//인가 설정
		http
			.authorizeHttpRequests(auth -> auth
					.requestMatchers("/", "/login", "/join", "/images/**").permitAll()
					.requestMatchers("/manager/**").hasRole("MANAGER")
					.requestMatchers("/reporter/**", "/api/**").hasAnyRole("REPORTER", "MANAGER")
					.anyRequest().authenticated());
		
		//jwt(토큰) 사용하기위한 설정	(2가지 추가) 	
		//세션 설정 : Stateless(무상태성) 
		http
			.sessionManagement((session) -> session
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		//세션 사용x -> 그래서 token 사용을 위한 Filter 적용(JWTFilter, LoginFilter) : 2가지 필터 필요(jwt필터, login필터)
		http.addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);
		
		 http															//인증에 필요한 객체   //jwt토큰을 만들어주는 Util 
         	.addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
}
