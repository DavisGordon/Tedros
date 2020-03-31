/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.tedros.global.brasil.ejb.bo;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import com.tedros.ejb.base.bo.TGenericBO;
import com.tedros.ejb.base.eao.ITGenericEAO;
import com.tedros.global.brasil.ejb.eao.PessoaEAO;
import com.tedros.global.brasil.model.Pessoa;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public final class PessoaBO extends TGenericBO<Pessoa> {

	private PessoaEAO eao = new PessoaEAO();
	
	@Override
	public ITGenericEAO<Pessoa> getEao() {
		return eao;
	}
	
	public List<Pessoa> pesquisar(EntityManager em, String nome, Date dataNascimento, String tipo, String tipoDocumento, String numero){
		return eao.pesquisar(em, nome, dataNascimento, tipo, tipoDocumento, numero);
	}
}
