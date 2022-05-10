/**
 * 
 */
package org.somos.server.campanha.bo;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.somos.model.AjudaCampanha;
import org.somos.model.Campanha;
import org.somos.model.FormaAjuda;
import org.somos.model.Periodo;
import org.somos.server.base.eao.TEntityEAO;
import org.somos.server.campanha.eao.AjudaCampanhaEAO;

import com.tedros.ejb.base.bo.TGenericBO;
import com.tedros.ejb.base.eao.ITGenericEAO;

/**
 * @author Davis Gordon
 *
 */
@RequestScoped
public class AjudaCampanhaBO extends TGenericBO<AjudaCampanha> {

	@Inject
	private AjudaCampanhaEAO eao;
	
	@Inject
	private TEntityEAO<Periodo> pEao;
	
	@Override
	public ITGenericEAO<AjudaCampanha> getEao() {
		return eao;
	}
	
	public List<AjudaCampanha> listarParaProcessamento(Campanha c, FormaAjuda fa){
		return eao.listarParaProcessamento(c, fa);
	}
	
	public void processar(AjudaCampanha ac) {
		
	}
	
	
	public void setProcessado(AjudaCampanha ac){
		ac.setDataProcessado(new Date());
		setDataProximo(ac);
	}

	public void setDataProximo(AjudaCampanha ac) {
		try {
			Periodo p = new Periodo();
			p.setNome(ac.getPeriodo());
			p = pEao.find(p);
			
			if(ac.getDetalheAjuda()==null && p.getTotalDias()!=null) {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.DAY_OF_MONTH, p.getTotalDias());
				ac.setDataProximo(cal.getTime());
			}else
				ac.setDataProximo(null);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	

}
