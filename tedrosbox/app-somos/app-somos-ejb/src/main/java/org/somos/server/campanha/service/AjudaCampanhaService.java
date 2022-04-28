/**
 * 
 */
package org.somos.server.campanha.service;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.somos.model.AjudaCampanha;
import org.somos.model.Campanha;
import org.somos.model.FormaAjuda;
import org.somos.server.campanha.bo.AjudaCampanhaBO;

import com.tedros.ejb.base.bo.TGenericBO;
import com.tedros.ejb.base.service.TEjbService;

/**
 * @author Davis Gordon
 *
 */
@Local
@Stateless(name="AjudaCampanhaService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class AjudaCampanhaService extends TEjbService<AjudaCampanha>  {

	@Inject
	private AjudaCampanhaBO bo;
	
	
	@Override
	public TGenericBO<AjudaCampanha> getBussinesObject() {
		return bo;
	}
	
	public List<AjudaCampanha> recuperar(Campanha c, FormaAjuda fa, String p, Integer diasAtras){
		return bo.recuperar(c, fa, p, diasAtras);
	}
	
	public List<AjudaCampanha> naoProcessados(Campanha c, FormaAjuda fa){
		return bo.naoProcessados(c, fa);
	}
	
	public List<AjudaCampanha> processarNoPeriodo(Campanha c, FormaAjuda fa){
		return bo.processarNoPeriodo(c, fa);
	}
	
}
