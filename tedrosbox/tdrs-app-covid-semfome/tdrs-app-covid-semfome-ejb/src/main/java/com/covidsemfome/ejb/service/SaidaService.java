/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.ejb.service;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.covidsemfome.ejb.bo.SaidaBO;
import com.covidsemfome.model.Saida;
import com.tedros.ejb.base.service.TEjbService;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@Local
@Stateless(name="SaidaService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class SaidaService extends TEjbService<Saida> {
	
	@Inject
	private SaidaBO bo;
	
	@Override
	public SaidaBO getBussinesObject() {
		return bo;
	}
	

}
