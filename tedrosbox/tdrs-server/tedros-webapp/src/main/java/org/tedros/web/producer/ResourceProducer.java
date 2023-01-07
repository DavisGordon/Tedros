/**
 * 
 */
package org.tedros.web.producer;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import org.tedros.core.controller.ITLoginController;
import org.tedros.core.security.model.TUser;
import org.tedros.server.result.TResult;
import org.tedros.server.result.TResult.TState;
import org.tedros.util.TEncriptUtil;
import org.tedros.util.TResourceUtil;
import org.tedros.web.domain.TWebMessage;


/**
 * @author Davis Gordon
 *
 */
@ApplicationScoped
public class ResourceProducer {
	
	@EJB
	private ITLoginController loginServ;
	
	private ResourceBundle res;

	private Properties webProp;
	
	@PostConstruct
	void init(){
		try {
			res = TResourceUtil.getResourceBundle("resource");
			String path = res.getString("user.resource.path");
			File f = new File(path);
			FileInputStream fis = new FileInputStream(f);
			webProp = new Properties();
			webProp.load(fis);
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Produces
	@ApplicationScoped
	@Named("loggedUser")
	public Item<TUser> getUser(){
		String login = webProp.getProperty("user.login");
		String pass = webProp.getProperty("user.pass");
		TResult<TUser> res = loginServ.login(login, TEncriptUtil.encript(pass));
		if(res.getState().equals(TState.SUCCESS))
			return new Item<>(res.getValue());
		else
			return null;
	}
	
	
	@Produces
	@ApplicationScoped
	@Named("errorMsg")
	public Item<String> getErrorMsg(){
		return  new Item<>(TWebMessage.ERROR);
	}
	
	
}
