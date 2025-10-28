package org.tedros.login.model;

import java.util.Properties;

import org.tedros.core.security.model.TProfile;
import org.tedros.server.model.ITModel;
import org.tedros.util.TResourceUtil;

public class Login implements ITModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String user;
	private String password;
	private String language;
	
	private TProfile profile;
	
	private String url;
	private String serverIp;
	
	public Login() {
		Properties p = TResourceUtil.getPropertiesFromConfFolder("remote-config.properties");
		if(p!=null){
			url = p.getProperty("url");
			serverIp = p.getProperty("server_ip");
		}
	}
	
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
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the serverIp
	 */
	public String getServerIp() {
		return serverIp;
	}
	/**
	 * @param serverIp the serverIp to set
	 */
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
}
