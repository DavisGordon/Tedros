package com.tedros.core.ejb.controller;

import java.util.List;

import javax.ejb.Remote;

import com.tedros.core.security.model.TAuthorization;
import com.tedros.ejb.base.controller.ITEjbController;
import com.tedros.ejb.base.result.TResult;

@Remote
public interface TAuthorizationController extends ITEjbController<TAuthorization>{

	@SuppressWarnings("rawtypes")
	TResult process(List<TAuthorization> newLst);

	
}
