/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.tedros.global.brasil.ejb.bo;

import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

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
@RequestScoped
public class PessoaBO extends TGenericBO<Pessoa> {

	@Inject
	private PessoaEAO eao;
	
	@Override
	public ITGenericEAO<Pessoa> getEao() {
		return eao;
	}
	
	public List<Pessoa> pesquisar(String nome, Date dataNascimento, String tipo, String tipoDocumento, String numero){
		return eao.pesquisar(nome, dataNascimento, tipo, tipoDocumento, numero);
	}
	
	public boolean isPessoaContatoExiste(String nome, String email, String telefone){
		return eao.isPessoaContatoExiste(nome, email, telefone);
	}
	
	
}
