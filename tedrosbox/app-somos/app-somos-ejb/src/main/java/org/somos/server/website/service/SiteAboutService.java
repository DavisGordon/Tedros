/**
 * 
 */
package org.somos.server.website.service;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.somos.model.SiteAbout;
import org.somos.server.base.bo.TEntityBO;

import com.tedros.ejb.base.bo.TGenericBO;
import com.tedros.ejb.base.service.TEjbService;

/**
 * @author Davis Gordon
 *
 */
@Local
@Stateless(name="SiteAboutService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class SiteAboutService extends TEjbService<SiteAbout>  {

	@Inject
	private TEntityBO<SiteAbout> bo;
	
	@Override
	public TGenericBO<SiteAbout> getBussinesObject() {
		return bo;
	}
	
}
