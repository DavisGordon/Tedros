/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.solidarity.ejb.service;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.solidarity.ejb.bo.VoluntarioBO;
import com.solidarity.model.Voluntario;
import com.tedros.ejb.base.service.TEjbService;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@Local
@Stateless(name="IVoluntarioService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class VoluntarioService extends TEjbService<Voluntario>  {
	
	@Inject
	private VoluntarioBO bo;
	
	@Override
	public VoluntarioBO getBussinesObject() {
		return bo;
	}
	
	
}
