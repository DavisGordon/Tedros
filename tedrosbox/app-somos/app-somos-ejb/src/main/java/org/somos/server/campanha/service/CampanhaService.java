/**
 * 
 */
package org.somos.server.campanha.service;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.somos.model.Campanha;
import org.somos.server.campanha.bo.CampanhaBO;

import com.tedros.ejb.base.bo.TGenericBO;
import com.tedros.ejb.base.service.TEjbService;

/**
 * @author Davis Gordon
 *
 */
@Local
@Stateless(name="CampanhaService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class CampanhaService extends TEjbService<Campanha>  {

	@Inject
	private CampanhaBO bo;
	
	@Override
	public TGenericBO<Campanha> getBussinesObject() {
		return bo;
	}
	
}
