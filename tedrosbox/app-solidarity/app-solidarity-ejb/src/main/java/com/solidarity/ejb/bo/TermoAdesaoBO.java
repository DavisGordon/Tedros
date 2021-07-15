/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.solidarity.ejb.bo;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.solidarity.ejb.eao.TermoAdesaoEAO;
import com.solidarity.model.TermoAdesao;
import com.tedros.ejb.base.bo.TGenericBO;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@RequestScoped
public class TermoAdesaoBO extends TGenericBO<TermoAdesao> {

	@Inject
	private TermoAdesaoEAO eao;
	
	@Override
	public TermoAdesaoEAO getEao() {
		return eao;
	}
	
	@Override
	public TermoAdesao save(TermoAdesao entidade) throws Exception {
		TermoAdesao ex = new TermoAdesao();
		ex.setStatus("ATIVADO");
		List<TermoAdesao> l = super.findAll(ex);
		
		if(l!=null && !l.isEmpty() && entidade.getStatus().equals("ATIVADO"))
			for (TermoAdesao e : l) {
				if(!entidade.isNew() && e.getId().equals(entidade.getId()))
					continue;
				e.setStatus("DESATIVADO");
				super.save(e);
			}
		
		return super.save(entidade);
	}
	

}
