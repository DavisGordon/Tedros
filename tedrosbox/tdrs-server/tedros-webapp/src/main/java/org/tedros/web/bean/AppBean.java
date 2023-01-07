/**
 * 
 */
package org.tedros.web.bean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.tedros.core.security.model.TUser;
import org.tedros.server.security.TAccessToken;
import org.tedros.web.producer.Item;


/**
 * @author Davis Gordon
 *
 */
@ApplicationScoped
public class AppBean {
	
	@Inject
	@Named("loggedUser")
	private Item<TUser> loggedUser; 
	
	public TAccessToken getToken() {
		return this.loggedUser.getValue().getAccessToken();
	}

}
