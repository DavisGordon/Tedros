package org.tedros.core.ejb.support;

import org.tedros.common.model.TFileEntity;
import org.tedros.core.ejb.service.TPropertieService;
import org.tedros.core.setting.model.TPropertie;
import org.tedros.core.support.TPropertieSupport;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;

@Stateless(name="TPropertieSupport")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TPropertieSupportImpl implements TPropertieSupport {
	
	@EJB
	private TPropertieService service;

	@Override
	public String getValue(String key) throws Exception {
		return service.getValue(key);
	}

	@Override
	public TFileEntity getFile(String key) throws Exception {
		return service.getFile(key);
	}

	@Override
	public boolean exist(String key) throws Exception {
		return service.exists(key);
	}

	@Override
	public boolean create(TPropertie propertie) throws Exception{
		return service.create(propertie);
	}

}
