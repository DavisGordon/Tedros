package com.tedros.ejb.base.service;

import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.ejb.base.entity.ITFileEntity;
import com.tedros.ejb.base.model.ITImportModel;

public interface ITEjbImportService<E extends ITEntity> {
	
	/**
	 * Retorna uma string em html com as regras para importacao
	 * */
	public ITImportModel getImportRules() throws Exception;
	
	/**
	 * Importa o arquivo
	 * */
	public void importFile(final ITFileEntity entity);
}
