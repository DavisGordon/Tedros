package org.tedros.server.ejb.service;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.tedros.server.cdi.bo.TImportFileEntityBO;
import org.tedros.server.entity.ITEntity;
import org.tedros.server.entity.ITFileEntity;
import org.tedros.server.model.ITImportModel;
import org.tedros.server.service.ITEjbImportService;

@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public abstract class TEjbImportService<E extends ITEntity> implements ITEjbImportService<E> {

	
	public abstract TImportFileEntityBO<E> getBusinessObject();

	@Override
	public ITImportModel getImportRules() throws Exception {
		return getBusinessObject().getImportRules();
	}

	@Override
	@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
	public List<E> importFile(ITFileEntity entity) {
		return getBusinessObject().importFile(entity);
	}
	
}
