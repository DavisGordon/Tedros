/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.server.estoque.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.covidsemfome.model.Cozinha;
import com.covidsemfome.model.Estoque;
import com.covidsemfome.report.model.EstoqueReportModel;
import com.covidsemfome.server.estoque.bo.EstoqueBO;
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
	
	public List<Estoque> pesquisar(List<Long> idsl, Cozinha coz, Date dataInicio, Date dataFim, String origem, String orderby, String ordertype){
		return bo.pesquisar(idsl, coz, dataInicio, dataFim, origem, orderby, ordertype);
	}
}
