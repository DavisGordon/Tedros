package org.tedros.core.ejb.service;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.tedros.core.cdi.bo.TCoreBO;
import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.ejb.service.TEjbService;

import org.tedros.core.security.model.TAuditLog;

@Local
@Stateless(name="TAuditLogService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TAuditLogService extends TEjbService<TAuditLog>	{

	@Inject
	private TCoreBO<TAuditLog> bo;
	
	@Override
	public ITGenericBO<TAuditLog> getBussinesObject() {
		return bo;
	}
	

}
