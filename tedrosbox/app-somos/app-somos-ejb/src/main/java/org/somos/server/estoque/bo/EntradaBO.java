/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 14/01/2014
 */
package org.somos.server.estoque.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.somos.model.Cozinha;
import org.somos.model.Entrada;
import org.somos.report.model.EstocavelModel;
import org.somos.report.model.EstocavelReportModel;
import org.somos.server.estoque.eao.EntradaEAO;

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
	
	public List<Entrada> pesquisar(List<Long> idsl, Cozinha coz, Date dataInicio, Date dataFim, String tipo, String orderby, String ordertype){
		return eao.pesquisar(idsl, coz, dataInicio, dataFim, tipo, orderby, ordertype);
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
		
		List<Entrada> lst = pesquisar(idsl, m.getCozinha(), m.getDataInicio(), m.getDataFim(), 
				m.getTipo(), m.getOrderBy(), m.getOrderType());
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
