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
	
	@Inject
	private AcaoBO acaoBO;

	@Override
	public ITGenericEAO<Voluntario> getEao() {
		// TODO Auto-generated method stub
		return eao;
	}
	
	public Voluntario recuperar(Acao acao, Pessoa pess){
		return eao.recuperar(acao, pess);
	}
	
	public void participarEmAcao(Pessoa pessoa, Long acaoId) throws Exception{
		Acao acao = new Acao();
		acao.setId(acaoId);
		acao = acaoBO.find(acao);
		
		Voluntario v = new Voluntario();
		v.setAcao(acao);
		v.setPessoa(pessoa);
		save(v);
		eao.getEntityManager().flush();
	}
	
	public void sairDaAcao(Pessoa pessoa, Long acaoId) throws Exception{
		Acao acao = new Acao();
		acao.setId(acaoId);
		
		Voluntario v  = recuperar(acao, pessoa);
		
		remove(v);
		eao.getEntityManager().flush();
	}

}
