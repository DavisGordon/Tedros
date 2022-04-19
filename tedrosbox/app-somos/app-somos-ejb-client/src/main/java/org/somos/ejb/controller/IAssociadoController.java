package org.somos.ejb.controller;

import javax.ejb.Remote;

import org.somos.model.Associado;

import com.tedros.ejb.base.controller.ITSecureEjbController;

@Remote
public interface IAssociadoController extends ITSecureEjbController<Associado>{
	
	
}
