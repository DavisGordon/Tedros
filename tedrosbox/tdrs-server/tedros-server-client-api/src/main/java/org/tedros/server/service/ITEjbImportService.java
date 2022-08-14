package org.tedros.server.service;

import java.util.List;

import org.tedros.server.entity.ITEntity;
import org.tedros.server.entity.ITFileEntity;
import org.tedros.server.model.ITImportModel;

public interface ITEjbImportService<E extends ITEntity> {
	
	/**
	 * Retorna uma string em html com as regras para importacao
	 * */
	public ITImportModel getImportRules() throws Exception;
	
	/**
	 * Importa o arquivo
	 * */
	public List<E> importFile(final ITFileEntity entity);
}
