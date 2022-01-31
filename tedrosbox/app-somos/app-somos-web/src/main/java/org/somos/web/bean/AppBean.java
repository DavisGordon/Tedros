/**
 * 
 */
package org.somos.web.bean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.somos.web.producer.Item;

import com.tedros.core.security.model.TUser;
import com.tedros.ejb.base.security.TAccessToken;


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
