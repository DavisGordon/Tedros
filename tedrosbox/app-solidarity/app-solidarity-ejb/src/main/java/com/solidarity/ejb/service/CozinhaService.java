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

import com.solidarity.ejb.bo.CozinhaBO;
import com.solidarity.model.Cozinha;
import com.tedros.ejb.base.service.TEjbService;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@Local
@Stateless(name="CozinhaService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class CozinhaService extends TEjbService<Cozinha> {
	
	@Inject
	private CozinhaBO bo;
	
	@Override
	public CozinhaBO getBussinesObject() {
		return bo;
	}
	

}
