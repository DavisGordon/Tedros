/**
 * 
 */
package org.somos.server.producer;

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

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import org.somos.server.domain.DomainMsg;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class ResourceProducer {
	
	private Properties p;
	private Properties smtpProp;
	
	@PostConstruct
	void init(){
		p = new Properties();
		try {
			InputStream is = loadContent("META-INF/resource.properties");
			p.load(is);
			is.close();
			String smtpResPath = p.getProperty("srv.email.resource.path");
			File f = new File(smtpResPath);
			FileInputStream fis = new FileInputStream(f);
			smtpProp = new Properties();
			smtpProp.load(fis);
			fis.close();
		} catch (IOException e) {
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
	@Named("host")
	public Item<String> getSrvHost(){
		return new Item<>(p.getProperty("srv.host"));
	}
	
	@Produces
	@RequestScoped
	@Named("csfEmail")
	public Item<String> getCsfEmail(){
		return new Item<>(p.getProperty("csf.email"));
	}
	
	@Produces
	@RequestScoped
	@Named("emailUser")
	public Item<String> getEmailUser(){
		return new Item<>(smtpProp.getProperty("srv.email.user"));
	}
	
	@Produces
	@RequestScoped
	@Named("emailPass")
	public Item<String> getEmailPass(){
		return new Item<>(smtpProp.getProperty("srv.email.pass"));
	}
	
	@Produces
	@RequestScoped
	@Named("emailSmtpHost")
	public Item<String> getEmailSmtpHost(){
		return new Item<>(smtpProp.getProperty("srv.email.smtp.host"));
	}
	
	@Produces
	@RequestScoped
	@Named("emailSmtpPort")
	public Item<String> getEmailSmtpPort(){
		return new Item<>(smtpProp.getProperty("srv.email.smtp.port"));
	}
	
	@Produces
	@RequestScoped
	@Named("emailSmtpSocketPort")
	public Item<String> getEmailSmtpSocketPort(){
		return new Item<>(smtpProp.getProperty("srv.email.smtp.socket.port"));
	}
	
	@Produces
	@RequestScoped
	@Named("emailTemplatePath")
	public Item<String> getEmailTemplatePath(){
		return new Item<>(p.getProperty("srv.email.template.path"));
	}
	
	@Produces
	@RequestScoped
	@Named("mailingTemplatePath")
	public Item<String> getMailingTemplatePath(){
		return new Item<>(p.getProperty("mailing.template.path"));
	}
	
	@Produces
	@RequestScoped
	@Named("errorMsg")
	public Item<String> getErrorMsg(){
		return new Item<>(String.format(DomainMsg.ERROR, getCsfEmail().getValue()));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
