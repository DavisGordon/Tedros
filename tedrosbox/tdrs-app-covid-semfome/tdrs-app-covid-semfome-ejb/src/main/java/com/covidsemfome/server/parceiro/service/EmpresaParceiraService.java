/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.server.parceiro.service;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.covidsemfome.parceiro.model.EmpresaParceira;
import com.covidsemfome.server.parceiro.bo.EmpresaParceiraBO;
import com.tedros.ejb.base.service.TEjbService;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@Local
@Stateless(name="EmpresaParceiraService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class EmpresaParceiraService extends TEjbService<EmpresaParceira> {
	
	@Inject
	private EmpresaParceiraBO bo;
	
	@Override
	public EmpresaParceiraBO getBussinesObject() {
		return bo;
	}
	

}
