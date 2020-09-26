package com.tedros.ejb.base.service;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.tedros.ejb.base.bo.TImportFileEntityBO;
import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.ejb.base.entity.ITFileEntity;
import com.tedros.ejb.base.model.ITImportModel;

@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public abstract class TEjbImportService<E extends ITEntity> implements ITEjbImportService<E> {

	
	public abstract TImportFileEntityBO<E> getBusinessObject();

	@Override
	public ITImportModel getImportRules() throws Exception {
		return getBusinessObject().getImportRules();
	}

	@Override
	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	public void importFile(ITFileEntity entity) {
		getBusinessObject().importFile(entity);
	}
	
}
