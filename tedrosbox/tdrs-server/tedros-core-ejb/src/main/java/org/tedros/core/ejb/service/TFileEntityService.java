package org.tedros.core.ejb.service;

import java.util.List;

import jakarta.ejb.Local;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;

import org.tedros.common.model.TFileEntity;
import org.tedros.core.cdi.bo.TFileEntityBO;
import org.tedros.server.cdi.bo.ITGenericBO;
import org.tedros.server.ejb.service.TEjbService;

@Local
@Stateless(name="TFileEntityService")
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class TFileEntityService extends TEjbService<TFileEntity>  {

	@Inject
	private TFileEntityBO bo;
	
	
	
	@Override
	public ITGenericBO<TFileEntity> getBussinesObject() {
		return bo;
	}
	
	public List<TFileEntity> find(List<String> owner, List<String> ext, Long maxSize, boolean loaded){
		return bo.find(owner, ext, maxSize, loaded);
	}

	public void loadBytes(TFileEntity entity) {
		 bo.loadBytes(entity);
	}


}
