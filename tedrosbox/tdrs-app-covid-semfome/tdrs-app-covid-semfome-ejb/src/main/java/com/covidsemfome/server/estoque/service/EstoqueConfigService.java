/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.server.estoque.service;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.covidsemfome.model.Cozinha;
import com.covidsemfome.model.EstoqueConfig;
import com.covidsemfome.server.estoque.bo.EstoqueConfigBO;
import com.tedros.ejb.base.service.TEjbService;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@Local
@Stateless(name="EstoqueConfigService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class EstoqueConfigService extends TEjbService<EstoqueConfig> {
	
	@Inject
	private EstoqueConfigBO bo;
	
	@Override
	public EstoqueConfigBO getBussinesObject() {
		return bo;
	}
	
	public List<EstoqueConfig> pesquisar(Cozinha cozinha){
		return bo.list(cozinha);
	}

}
