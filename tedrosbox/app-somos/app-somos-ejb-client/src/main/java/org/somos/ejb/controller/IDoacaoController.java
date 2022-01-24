package org.somos.ejb.controller;

import javax.ejb.Remote;

import org.somos.model.Doacao;

import com.tedros.ejb.base.controller.ITSecureEjbController;

@Remote
public interface IDoacaoController extends ITSecureEjbController<Doacao>{
	
	
}
