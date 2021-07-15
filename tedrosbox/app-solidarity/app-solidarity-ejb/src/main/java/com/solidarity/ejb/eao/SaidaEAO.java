/**
 * 
 */
package com.solidarity.ejb.eao;

import javax.enterprise.context.RequestScoped;

import com.solidarity.model.Saida;
import com.solidarity.model.SaidaItem;
import com.tedros.ejb.base.eao.TGenericEAO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class SaidaEAO extends TGenericEAO<Saida> {

	@Override
	public void beforePersist(Saida entidade)	throws Exception {
		childsReference(entidade);
	}
	
	@Override
	public void beforeMerge(Saida entidade) throws Exception {
		childsReference(entidade);
	}
	
	@Override
	public void beforeRemove(Saida entidade) throws Exception {
		childsReference(entidade);
	}
	
	
	public void childsReference(Saida entidade)throws Exception {
		
		if(entidade.getItens()!=null){
			for(final SaidaItem e : entidade.getItens())
				e.setSaida(entidade);
		}
		
	}
}
