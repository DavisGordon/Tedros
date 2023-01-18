/**
 * 
 */
package org.tedros.web.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.tedros.env.entity.WebUser;

/**
 * @author Davis Gordon
 *
 */
@Named("userBean")
@SessionScoped
public class UserBean implements Serializable{

	private static final long serialVersionUID = 700314364541128827L;
	private WebUser user;
	
	@PostConstruct
	public void init(){
		user = new WebUser();
	}

	/**
	 * @return the user
	 */
	public WebUser getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(WebUser user) {
		this.user = user;
	}
}
