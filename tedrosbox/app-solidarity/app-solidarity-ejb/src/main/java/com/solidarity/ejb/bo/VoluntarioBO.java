/**
 * 
 */
package com.solidarity.ejb.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.solidarity.ejb.eao.VoluntarioEAO;
import com.solidarity.model.Acao;
import com.solidarity.model.Pessoa;
import com.solidarity.model.Voluntario;
import com.tedros.ejb.base.bo.TGenericBO;
import com.tedros.ejb.base.eao.ITGenericEAO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class VoluntarioBO extends TGenericBO<Voluntario> {
	
	@Inject
	private VoluntarioEAO eao;
	
	
	@Override
	public ITGenericEAO<Voluntario> getEao() {
		return eao;
	}
	
	public Voluntario recuperar(Acao acao, Pessoa pess){
		return eao.recuperar(acao, pess);
	}
	
	public boolean isVoluntario(Pessoa pess){
		return eao.isVoluntario(pess);
	}
	
	public void sairDaAcao(Pessoa pessoa, Long acaoId) throws Exception{
		Acao acao = new Acao();
		acao.setId(acaoId);
		
		Voluntario v  = recuperar(acao, pessoa);
		
		remove(v);
	}

}
