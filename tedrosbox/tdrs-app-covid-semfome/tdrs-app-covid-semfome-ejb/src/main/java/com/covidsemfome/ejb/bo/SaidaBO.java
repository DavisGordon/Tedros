/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.ejb.bo;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.covidsemfome.ejb.eao.SaidaEAO;
import com.covidsemfome.model.Entrada;
import com.covidsemfome.model.Saida;
import com.covidsemfome.report.model.EstocavelModel;
import com.covidsemfome.report.model.EstocavelReportModel;
import com.tedros.ejb.base.bo.TGenericBO;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@RequestScoped
public class SaidaBO extends TGenericBO<Saida> {

	@Inject
	private SaidaEAO eao;
	
	@Override
	public SaidaEAO getEao() {
		return eao;
	}
	
	public EstocavelReportModel pesquisar(EstocavelReportModel m){
		String ids = m.getIds();
		List<Long> idsl = null;
		if(ids!=null){
			idsl = new ArrayList<>();
			String[] arr = ids.split(",");
			for(String i : arr)
				idsl.add(Long.valueOf(i));
		}
		
		List<Saida> lst = eao.pesquisar(idsl, m.getCozinha(), m.getDataInicio(), m.getDataFim());
		if(lst!=null) {
			List<EstocavelModel> itens = new ArrayList<>();
			for(Saida a : lst){
				itens.add(new EstocavelModel(a));
			}
			m.setResult(itens);
		}
		
		return m;
	}

}
