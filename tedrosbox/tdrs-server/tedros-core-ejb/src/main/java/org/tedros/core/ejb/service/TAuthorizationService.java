package org.tedros.core.ejb.service;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.tedros.core.cdi.bo.TAuthorizationBO;
import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.ejb.service.TEjbService;

import org.tedros.core.security.model.TAuthorization;

@Local
@Stateless(name="TAuthorizationService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TAuthorizationService extends TEjbService<TAuthorization>	{

	@Inject
	private TAuthorizationBO bo;
	
	@Override
	public ITGenericBO<TAuthorization> getBussinesObject() {
		return bo;
	}
	
	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	public List<String> process(List<TAuthorization> newLst, List<TAuthorization> oldLst) throws Exception{
		return bo.process(newLst, oldLst);
	}

}
