/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.tedros.global.brasil.ejb.bo;

import javax.persistence.EntityManager;

import com.tedros.ejb.base.bo.TGenericBO;
import com.tedros.ejb.base.eao.ITGenericEAO;
import com.tedros.global.brasil.ejb.eao.DocumentoEAO;
import com.tedros.global.brasil.model.Documento;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public final class DocumentoBO extends TGenericBO<Documento> {

	private DocumentoEAO eao = new DocumentoEAO();
	
	@Override
	public ITGenericEAO<Documento> getEao() {
		return eao;
	}
	
	public void excluirTodos(EntityManager em, final Long idPessoa)throws Exception{
		eao.excluirTodos(em, idPessoa);
	}

}
