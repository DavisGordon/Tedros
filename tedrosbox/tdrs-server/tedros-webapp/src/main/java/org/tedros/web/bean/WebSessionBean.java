/**
 * 
 */
package org.tedros.web.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.tedros.env.entity.WebSession;

/**
 * @author Davis Gordon
 *
 */
@Named("session")
@SessionScoped
public class WebSessionBean implements Serializable{

	private static final long serialVersionUID = 700314364541128827L;
	private WebSession session;
	
	@PostConstruct
	public void init(){
		session = new WebSession();
	}

	/**
	 * @return the session
	 */
	public WebSession get() {
		return session;
	}

	/**
	 * @param session the session to set
	 */
	public void set(WebSession session) {
		this.session = session;
	}
}
