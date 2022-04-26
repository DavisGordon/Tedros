/**
 * 
 */
package org.somos.server.campanha.service;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.somos.model.AjudaCampanha;
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
	
}
