/**
 * 
 */
package org.somos.web.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.somos.model.User;

/**
 * @author Davis Gordon
 *
 */
@Named("covidUserBean")
@SessionScoped
public class CovidUserBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2879140772252995694L;
	private User user;
	
	@PostConstruct
	public void init(){
		user = new User();
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
}
