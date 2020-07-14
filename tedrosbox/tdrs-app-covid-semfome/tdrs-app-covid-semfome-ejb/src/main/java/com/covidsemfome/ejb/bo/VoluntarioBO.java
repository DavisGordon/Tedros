/**
 * 
 */
package com.covidsemfome.ejb.bo;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.covidsemfome.ejb.eao.VoluntarioEAO;
import com.covidsemfome.model.Acao;
import com.covidsemfome.model.Pessoa;
import com.covidsemfome.model.Voluntario;
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
