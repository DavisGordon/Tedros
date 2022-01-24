/**
 * 
 */
package org.somos.server.acao.bo;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.somos.model.Acao;
import org.somos.model.Pessoa;
import org.somos.model.TipoAjuda;
import org.somos.model.Voluntario;
import org.somos.report.model.VoluntarioItemModel;
import org.somos.report.model.VoluntarioReportModel;
import org.somos.server.acao.eao.VoluntarioEAO;

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
	
	public VoluntarioReportModel pesquisar(VoluntarioReportModel m){
		String ids = m.getIds();
		List<Long> idsl = null;
		if(ids!=null){
			idsl = new ArrayList<>();
			String[] arr = ids.split(",");
			for(String i : arr)
				idsl.add(Long.valueOf(i));
		}
		List<Long> idst = null;
		if(m.getTiposAjuda()!=null){
			idst = new ArrayList<>();
			for(TipoAjuda i : m.getTiposAjuda())
				idst.add(i.getId());
		}
		List<Voluntario> lst = eao.pesquisar(idsl, m.getTitulo(), m.getStatus(), m.getNome(), idst, 
				m.getDataInicio(), m.getDataFim(), m.getOrderBy(), m.getOrderType());
		
		if(lst!=null){
			List<VoluntarioItemModel> itens = new ArrayList<>();
			for(Voluntario v : lst)
				itens.add(new VoluntarioItemModel(v));
			
			m.setResult(itens);
		}
		
		
		return m;
	}
	
	public void sairDaAcao(Pessoa pessoa, Long acaoId) throws Exception{
		Acao acao = new Acao();
		acao.setId(acaoId);
		
		Voluntario v  = recuperar(acao, pessoa);
		
		remove(v);
	}

}
