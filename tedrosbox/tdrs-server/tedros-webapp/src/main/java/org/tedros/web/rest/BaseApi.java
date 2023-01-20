package org.tedros.web.rest;

import javax.enterprise.inject.Any;
import javax.inject.Inject;
import javax.inject.Named;

import org.tedros.env.domain.WebUserType;
import org.tedros.web.bean.AppBean;
import org.tedros.web.bean.WebLanguageBean;
import org.tedros.web.producer.Item;


public class BaseApi {
	
	@Inject
	@Named("errorMsg")
	protected Item<String> error;
	
	@Inject @Any
	protected WebLanguageBean lang;
	
	@Inject
	protected AppBean appBean;
	
	protected WebUserType getType(String utype) {
		return utype.equals("c") 
				? WebUserType.CUSTOMER
						: WebUserType.COMMUNITY;
	}
	
}
