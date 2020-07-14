/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.ejb.service;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;

import com.covidsemfome.ejb.bo.DoacaoBO;
import com.covidsemfome.model.Doacao;
import com.covidsemfome.model.TipoAjuda;
import com.tedros.ejb.base.service.TEjbService;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 **/
@Local
@Stateless(name="DoacaoService")
public class DoacaoService extends TEjbService<Doacao> {
	
	@Inject
	private DoacaoBO bo;
	
	@Override
	public DoacaoBO getBussinesObject() {
		return bo;
	}
	
	public List<Doacao> pesquisar(String nome, Date dataInicio, Date dataFim, Long acaoId, TipoAjuda tipoAjuda){
		return getBussinesObject().pesquisar(nome, dataInicio, dataFim, acaoId, tipoAjuda);
	}

	
}
