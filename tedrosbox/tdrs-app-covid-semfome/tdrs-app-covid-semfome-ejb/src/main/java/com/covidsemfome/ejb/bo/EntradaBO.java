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

import com.covidsemfome.ejb.eao.EntradaEAO;
import com.covidsemfome.model.Entrada;
import com.covidsemfome.model.Estoque;
import com.covidsemfome.report.model.EstocavelModel;
import com.covidsemfome.report.model.EstocavelReportModel;
import com.covidsemfome.report.model.EstoqueModel;
import com.covidsemfome.report.model.EstoqueReportModel;
import com.tedros.ejb.base.bo.TGenericBO;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
@RequestScoped
public class EntradaBO extends TGenericBO<Entrada> {

	@Inject
	private EntradaEAO eao;
	
	@Override
	public EntradaEAO getEao() {
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
		
		List<Entrada> lst = eao.pesquisar(idsl, m.getCozinha(), m.getDataInicio(), m.getDataFim(), m.getTipo());
		if(lst!=null) {
			List<EstocavelModel> itens = new ArrayList<>();
			for(Entrada a : lst){
				itens.add(new EstocavelModel(a));
			}
			m.setResult(itens);
		}
		
		return m;
	}

}
