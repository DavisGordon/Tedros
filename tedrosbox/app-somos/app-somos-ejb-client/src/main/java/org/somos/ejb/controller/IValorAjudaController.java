package org.somos.ejb.controller;

import javax.ejb.Remote;

import org.somos.model.ValorAjuda;

import com.tedros.ejb.base.controller.ITSecureEjbController;

@Remote
public interface IValorAjudaController extends ITSecureEjbController<ValorAjuda>{
	
	
}
