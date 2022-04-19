package org.somos.ejb.controller;

import javax.ejb.Remote;

import org.somos.model.FormaAjuda;

import com.tedros.ejb.base.controller.ITSecureEjbController;

@Remote
public interface IFormaAjudaController extends ITSecureEjbController<FormaAjuda>{
	
	
}
