package com.ecrops.config;

import java.util.ArrayList;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ecrops.entity.AppUser;
import com.ecrops.entity.Roles;


public class UserPrincipal implements UserDetails {
	
	private AppUser user;

	public UserPrincipal(AppUser user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//return Collections.singleton(new SimpleGrantedAuthority("USER"));
		List<GrantedAuthority> authorities = new ArrayList<>();
		for(Roles role: user.getRoles()){
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		//return Collections.singleton(new SimpleGrantedAuthority("USER"));
		return authorities;
	}

	@Override
	public String getPassword() {
		
		return user.getEncpassword();
	}

	@Override
	public String getUsername() {
	
		return user.getUserid();
	}
	
	public int getDcode() {
		return user.getDcode();
	}

	public int getMcode() {
		return user.getMcode();
	}

	public String getStatus() {
		return user.getStatus();
	}
	 
	 
//	 public int getOpunitcode() { 
//	 user.getOpunitcode(); }
	
	
	public String getTypeUser() {
		return user.getType_user();
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
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