package org.somos.ejb.controller;

import javax.ejb.Remote;

import org.somos.model.CampanhaMailConfig;

import com.tedros.ejb.base.controller.ITSecureEjbController;

@Remote
public interface ICampanhaMailConfigController extends ITSecureEjbController<CampanhaMailConfig>{
	
	
}
