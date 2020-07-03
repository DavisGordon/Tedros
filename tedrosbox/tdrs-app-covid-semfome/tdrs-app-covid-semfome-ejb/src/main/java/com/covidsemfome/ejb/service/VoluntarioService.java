/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.ejb.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.covidsemfome.ejb.bo.VoluntarioBO;
import com.covidsemfome.model.Voluntario;
import com.tedros.ejb.base.service.TEjbService;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@Stateless(name="IVoluntarioService")
public class VoluntarioService extends TEjbService<Voluntario> implements IVoluntarioService {
	
	@Inject
	private VoluntarioBO bo;
	
	@Override
	public VoluntarioBO getBussinesObject() {
		return bo;
	}
	
	
}
