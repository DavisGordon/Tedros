package org.tedros.web.rest;

import javax.inject.Inject;
import javax.inject.Named;

import org.tedros.web.bean.AppBean;
import org.tedros.web.producer.Item;


public class BaseApi {
	
	@Inject
	@Named("errorMsg")
	protected Item<String> error;
	
	@Inject
	protected AppBean appBean;
	
}
