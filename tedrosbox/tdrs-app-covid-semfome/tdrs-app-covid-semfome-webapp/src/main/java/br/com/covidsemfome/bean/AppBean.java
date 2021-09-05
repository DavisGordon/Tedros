/**
 * 
 */
package br.com.covidsemfome.bean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.tedros.core.security.model.TUser;
import com.tedros.ejb.base.security.TAccessToken;

import br.com.covidsemfome.producer.Item;


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
