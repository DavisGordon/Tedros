package org.tedros.core.ejb.service;

import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;

import org.tedros.core.cdi.bo.TCoreBO;
import org.tedros.core.security.model.TAuditLog;
import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.ejb.service.TEjbService;

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
