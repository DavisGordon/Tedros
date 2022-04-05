package com.tedros.core.ejb.service;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.tedros.core.ejb.bo.TCoreBO;
import com.tedros.core.security.model.TAuditLog;
import com.tedros.ejb.base.bo.ITGenericBO;
import com.tedros.ejb.base.service.TEjbService;

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
