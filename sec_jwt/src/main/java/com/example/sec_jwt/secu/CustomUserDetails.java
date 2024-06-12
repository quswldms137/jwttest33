package com.example.sec_jwt.secu;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.sec_jwt.entity.ReporterEntity;
								// 시큐리티 사용하기 위해 필요한 클래스 CustomUserDetails : implements UserDetails 위한
public class CustomUserDetails implements UserDetails{

	private ReporterEntity member; 
	//생성자주입 방식 
	public CustomUserDetails(ReporterEntity member) {
		this.member = member;
	}
	
	@Override                   //제네릭 ? 
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<GrantedAuthority> collection = new ArrayList<>(); 
		collection.add(new GrantedAuthority() {  //GrantedAuthority 생성자를 통해 

			@Override
			public String getAuthority() {
				return member.getRole(); //롤 정보를 얻기위해 (사용자정보를 얻기위해 객체에 넣은것)
			}
		});
		
		return collection;
	}

	@Override
	public String getPassword() {
		return member.getPassword();
	}

	@Override
	public String getUsername() {
		return member.getUsername();
	}
	
	public String getRole() {
		return member.getRole();
	}
	
	public String getName() {
		return member.getName();
	}
	
	public String getEmail() {
		return member.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true; //true : 동작o
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
