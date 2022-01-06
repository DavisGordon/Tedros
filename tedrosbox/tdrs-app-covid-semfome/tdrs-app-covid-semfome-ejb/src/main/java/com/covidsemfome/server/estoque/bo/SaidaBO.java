/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package com.covidsemfome.server.estoque.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.covidsemfome.model.Cozinha;
import com.covidsemfome.model.Saida;
import com.covidsemfome.report.model.EstocavelModel;
import com.covidsemfome.report.model.EstocavelReportModel;
import com.covidsemfome.server.estoque.eao.SaidaEAO;
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
	
	public List<Saida> pesquisar(List<Long> idsl, Cozinha coz, Date dataInicio, Date dataFim, String orderby, String ordertype){
		return eao.pesquisar(idsl, coz, dataInicio, dataFim, orderby, ordertype);
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
		
		List<Saida> lst = pesquisar(idsl, m.getCozinha(), m.getDataInicio(), m.getDataFim(), m.getOrderBy(), m.getOrderType());
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
