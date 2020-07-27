/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.ejb.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.covidsemfome.ejb.eao.DoacaoEAO;
import com.covidsemfome.model.Doacao;
import com.covidsemfome.model.TipoAjuda;
import com.tedros.ejb.base.bo.TGenericBO;
import com.tedros.ejb.base.eao.ITGenericEAO;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@RequestScoped
public class DoacaoBO extends TGenericBO<Doacao> {

	@Inject
	private DoacaoEAO eao;
	
	@Override
	public ITGenericEAO<Doacao> getEao() {
		return eao;
	}
	
	public List<Doacao> pesquisar(String nome, Date dataInicio, Date dataFim, String acaoId, List<TipoAjuda> tipoAjuda){
		List<Long> acoes = null;
		List<Long> tipos = null;
		if(acaoId!=null){
			acoes = new ArrayList<>();
			String[] arr = acaoId.split(",");
			for (String s : arr) {
				acoes.add(Long.valueOf(s));
			}
		}
		
		if(tipoAjuda!=null){
			tipos = new ArrayList<>();
			for(TipoAjuda t : tipoAjuda)
				tipos.add(t.getId());
		}
		
		return eao.pesquisar(nome, dataInicio, dataFim, acoes, tipos);
	}

}
