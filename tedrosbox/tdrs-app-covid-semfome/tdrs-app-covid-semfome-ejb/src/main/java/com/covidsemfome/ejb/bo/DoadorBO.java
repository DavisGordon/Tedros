/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.ejb.bo;

import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.covidsemfome.ejb.eao.DoadorEAO;
import com.covidsemfome.model.Doador;
import com.tedros.ejb.base.bo.TGenericBO;
import com.tedros.ejb.base.eao.ITGenericEAO;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@RequestScoped
public class DoadorBO extends TGenericBO<Doador> {
	
	@Inject
	private DoadorEAO eao;
	
	@Override
	public ITGenericEAO<Doador> getEao() {
		return eao;
	}
	
	public List<Doador> pesquisar(String nome, Date dataNascimento){
		return eao.pesquisar(nome, dataNascimento);
	}
}
