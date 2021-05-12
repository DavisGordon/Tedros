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

import com.covidsemfome.ejb.bo.UFBO;
import com.covidsemfome.model.UF;
import com.tedros.ejb.base.service.TEjbService;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@Local
@Stateless(name="UFService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class UFService extends TEjbService<UF> {
	
	@Inject
	private UFBO bo;
	
	@Override
	public UFBO getBussinesObject() {
		return bo;
	}
	

}
