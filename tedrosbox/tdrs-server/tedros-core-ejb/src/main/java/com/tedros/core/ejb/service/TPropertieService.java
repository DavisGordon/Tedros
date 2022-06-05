package com.tedros.core.ejb.service;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import com.tedros.common.model.TFileEntity;
import com.tedros.core.ejb.bo.TPropertieBO;
import com.tedros.core.setting.model.TPropertie;
import com.tedros.ejb.base.bo.ITGenericBO;
import com.tedros.ejb.base.service.TEjbService;

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
