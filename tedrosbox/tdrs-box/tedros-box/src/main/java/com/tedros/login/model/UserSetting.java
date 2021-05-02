package com.tedros.login.model;

import com.tedros.core.security.model.TProfile;
import com.tedros.core.security.model.TUser;
import com.tedros.ejb.base.model.ITModel;

public class UserSetting implements ITModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5609075621287886648L;
	
	private String language;
	private TProfile profile;
	
	/*private Set<TProfile> profiles;*/
	
	//private TUser user;
	
	/**
	 * @param user
	 */
	public UserSetting(TUser user) {
		//this.user = user;
		//this.profile = user.getActiveProfile();
//		this.profiles = user.getProfiles();
	}
	public TProfile getProfile() {
		return profile;
	}
	public void setProfile(TProfile profile) {
		this.profile = profile;
	}
	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}
	/**
	 * @param language the language to set
	 */
	public void setLanguage(String language) {
		this.language = language;
	}
	
	
	
	
}
