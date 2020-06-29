/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.ejb.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.covidsemfome.ejb.bo.TipoAjudaBO;
import com.covidsemfome.model.TipoAjuda;
import com.tedros.ejb.base.service.TEjbService;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@Stateless(name="ITipoAjudaService")
public class TipoAjudaService extends TEjbService<TipoAjuda> implements ITipoAjudaService {
	
	@Inject
	private TipoAjudaBO bo;
	
	@Override
	public TipoAjudaBO getBussinesObject() {
		return bo;
	}
	

}
