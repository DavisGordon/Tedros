package org.tedros.core.ejb.service;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;

import org.tedros.common.model.TFileEntity;
import org.tedros.core.cdi.bo.TPropertieBO;
import org.tedros.core.setting.model.TPropertie;
import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.ejb.service.TEjbService;

@LocalBean
@Stateless(name="TPropertieService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TPropertieService extends TEjbService<TPropertie>	{

	@Inject
	private TPropertieBO bo;
	
	@Override
	public ITGenericBO<TPropertie> getBussinesObject() {
		return bo;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void buildProperties() throws Exception {
		bo.buildProperties();
	}
	

	public String getValue(String key) {
		return bo.getValue(key);
	}
	
	public TFileEntity getFile(String key){
		return bo.getFile(key);
	}
	

}
