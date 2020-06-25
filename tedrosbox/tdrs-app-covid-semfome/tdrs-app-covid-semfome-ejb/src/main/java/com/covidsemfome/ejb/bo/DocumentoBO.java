/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.ejb.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.covidsemfome.ejb.eao.DocumentoEAO;
import com.covidsemfome.model.Documento;
import com.tedros.ejb.base.bo.TGenericBO;
import com.tedros.ejb.base.eao.ITGenericEAO;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@RequestScoped
public class DocumentoBO extends TGenericBO<Documento> {

	@Inject
	private DocumentoEAO eao;
	
	@Override
	public ITGenericEAO<Documento> getEao() {
		return eao;
	}
	
	public void excluirTodos(final Long idPessoa)throws Exception{
		eao.excluirTodos(idPessoa);
	}

}
