package org.somos.ejb.controller;

import javax.ejb.Remote;

import org.somos.model.Campanha;

import com.tedros.ejb.base.controller.ITSecureEjbController;

@Remote
public interface ICampanhaController extends ITSecureEjbController<Campanha>{
	
	
}
