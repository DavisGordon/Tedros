/**
 * 
 */
package org.somos.server.campanha.bo;

import java.util.ArrayList;
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
	
	public List<AjudaCampanha> recuperar(Campanha c, FormaAjuda fa, String p, Integer diasAtras){
		return eao.recuperar(c, fa, p, diasAtras);
	}
	
	public List<AjudaCampanha> naoProcessados(Campanha c, FormaAjuda fa){
		return eao.recuperar(c, fa, null, null);
	}
	
	public List<AjudaCampanha> processarNoPeriodo(Campanha c, FormaAjuda fa){
		try {
			List<Periodo> l = pEao.listAll(Periodo.class);
			List<AjudaCampanha> lst = new ArrayList<>();
			l.forEach(p->{
				lst.addAll(eao.recuperar(c, fa, p.getNome(), p.getTotalDias()));
			});
			
			return lst;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
