package com.tedros.core.ejb.controller;

import javax.ejb.Remote;

import com.tedros.core.setting.model.TPropertie;
import com.tedros.ejb.base.controller.ITSecureEjbController;

@Remote
public interface TPropertieController extends ITSecureEjbController<TPropertie>{

	
}
