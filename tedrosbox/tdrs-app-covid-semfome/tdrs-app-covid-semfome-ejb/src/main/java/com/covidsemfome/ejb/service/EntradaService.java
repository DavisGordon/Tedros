/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.ejb.service;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.covidsemfome.ejb.bo.EntradaBO;
import com.covidsemfome.model.Entrada;
import com.tedros.ejb.base.service.TEjbService;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@Local
@Stateless(name="EntradaService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class EntradaService extends TEjbService<Entrada> {
	
	@Inject
	private EntradaBO bo;
	
	@Override
	public EntradaBO getBussinesObject() {
		return bo;
	}
	

}
