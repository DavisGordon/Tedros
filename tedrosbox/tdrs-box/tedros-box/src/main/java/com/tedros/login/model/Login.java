package com.tedros.login.model;

import com.tedros.core.security.model.TProfile;
import com.tedros.ejb.base.model.ITModel;

public class Login implements ITModel {

	private String user;
	private String password;
	private String language;
	
	private TProfile profile;
	
	
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public TProfile getProfile() {
		return profile;
	}
	public void setProfile(TProfile profile) {
		this.profile = profile;
	}
	
	
	
	
}
