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

import com.solidarity.ejb.bo.EstoqueBO;
import com.solidarity.model.Estoque;
import com.solidarity.report.model.EstoqueReportModel;
import com.tedros.ejb.base.service.TEjbService;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@Local
@Stateless(name="EstoqueService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class EstoqueService extends TEjbService<Estoque> {
	
	@Inject
	private EstoqueBO bo;
	
	@Override
	public EstoqueBO getBussinesObject() {
		return bo;
	}
	
	@TransactionAttribute(value = TransactionAttributeType.NEVER)
	public EstoqueReportModel pesquisar(EstoqueReportModel m){
		return bo.pesquisar(m);
	}
}
