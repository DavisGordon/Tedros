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

import com.covidsemfome.ejb.bo.ProducaoBO;
import com.covidsemfome.model.Producao;
import com.tedros.ejb.base.service.TEjbService;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@Local
@Stateless(name="ProducaoService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class ProducaoService extends TEjbService<Producao> {
	
	@Inject
	private ProducaoBO bo;
	
	@Override
	public ProducaoBO getBussinesObject() {
		return bo;
	}
	

}
