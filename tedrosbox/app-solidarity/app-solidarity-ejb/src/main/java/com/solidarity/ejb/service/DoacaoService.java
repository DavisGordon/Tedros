/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.solidarity.ejb.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.solidarity.ejb.bo.DoacaoBO;
import com.solidarity.model.Doacao;
import com.solidarity.model.TipoAjuda;
import com.tedros.ejb.base.service.TEjbService;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 **/
@Local
@Stateless(name="DoacaoService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class DoacaoService extends TEjbService<Doacao> {
	
	@Inject
	private DoacaoBO bo;
	
	@Override
	public DoacaoBO getBussinesObject() {
		return bo;
	}
	
	public List<Doacao> pesquisar(String nome, Date dataInicio, Date dataFim, String acaoId, List<TipoAjuda> tipoAjuda){
		return getBussinesObject().pesquisar(nome, dataInicio, dataFim, acaoId, tipoAjuda);
	}

	
}
