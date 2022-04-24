package org.somos.web.rest.service;

import javax.inject.Inject;
import javax.inject.Named;

import org.somos.web.bean.AppBean;
import org.somos.web.producer.Item;


public class BaseApi {
	
	@Inject
	@Named("errorMsg")
	protected Item<String> error;
	
	@Inject
	protected AppBean appBean;
	
}
