/**
 * 
 */
package br.com.covidsemfome.producer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import com.covidsemfome.domain.TWebMessage;
import com.tedros.core.ejb.controller.ITLoginController;
import com.tedros.core.security.model.TUser;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.result.TResult.EnumResult;
import com.tedros.util.TEncriptUtil;
import com.tedros.util.TResourceUtil;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class ResourceProducer {
	
	@EJB
	private ITLoginController loginServ;
	
	private ResourceBundle res;

	private Properties webProp;
	
	@PostConstruct
	void init(){
		try {
			res = TResourceUtil.getResourceBundle("resource");
			/*InputStream is = loadContent("META-INF/resource.properties");
			p.load(is);
			is.close();*/
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
	
	private InputStream loadContent(String path) {

	    final String resourceName = path;
	    final ClassLoader classLoader = getClass().getClassLoader();
	    InputStream stream = null;
	    try {
	        stream = AccessController.doPrivileged(
	                new PrivilegedExceptionAction<InputStream>() {
	                    public InputStream run() throws IOException {
	                        InputStream is = null;
	                        URL url = classLoader.getResource(resourceName);
	                        if (url != null) {
	                            URLConnection connection = url.openConnection();
	                            if (connection != null) {
	                                connection.setUseCaches(false);
	                                is = connection.getInputStream();
	                            }
	                        }
	                        return is;
	                    }
	                });
	    } catch (PrivilegedActionException e) {
	        e.printStackTrace();
	    }
	    return stream;
	}
	
	
	@Produces
	@RequestScoped
	@Named("loggedUser")
	public Item<TUser> getUser(){
		String login = webProp.getProperty("user.login");
		String pass = webProp.getProperty("user.pass");
		TResult<TUser> res = loginServ.login(login, TEncriptUtil.encript(pass));
		if(res.getResult().equals(EnumResult.SUCESS))
			return new Item<>(res.getValue());
		else
			return null;
	}
	
	
	@Produces
	@RequestScoped
	@Named("errorMsg")
	public Item<String> getErrorMsg(){
		return  new Item<>(TWebMessage.ERROR);
	}
	
	
}
