/**
 * 
 */
package com.solidarity.ejb.eao;

import javax.enterprise.context.RequestScoped;

import com.solidarity.model.Entrada;
import com.solidarity.model.EntradaItem;
import com.tedros.ejb.base.eao.TGenericEAO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class EntradaEAO extends TGenericEAO<Entrada> {

	@Override
	public void beforePersist(Entrada entidade)	throws Exception {
		childsReference(entidade);
	}
	
	@Override
	public void beforeMerge(Entrada entidade) throws Exception {
		childsReference(entidade);
	}
	
	@Override
	public void beforeRemove(Entrada entidade) throws Exception {
		childsReference(entidade);
	}
	
	
	public void childsReference(Entrada entidade)throws Exception {
		
		if(entidade.getItens()!=null){
			for(final EntradaItem e : entidade.getItens())
				e.setEntrada(entidade);
		}
		
	}
	
}
