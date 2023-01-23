/**
 * 
 */
package org.tedros.web.bean;

import java.io.Serializable;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * @author Davis Gordon
 *
 */
@Named("lang")
@SessionScoped
public class WebLanguageBean implements Serializable{

	private static final long serialVersionUID = 700314364541128827L;
	private Value<String> value;
	
	@PostConstruct
	public void init(){
		value = new Value<>();
	}

	/**
	 * @return the value
	 */
	public Locale get() {
		return value.getObj()!=null 
				? new Locale(value.getObj())
						: null;
	}

	/**
	 * @param lang the value to set
	 */
	public void set(String lang) {
		this.value.setObj(lang);
	}
}
