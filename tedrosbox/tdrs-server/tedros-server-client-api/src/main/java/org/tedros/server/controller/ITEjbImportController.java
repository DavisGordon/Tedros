package org.tedros.server.controller;

import org.tedros.server.entity.ITEntity;
import org.tedros.server.entity.ITFileEntity;
import org.tedros.server.result.TResult;
import org.tedros.server.security.TAccessToken;

public interface ITEjbImportController<E extends ITEntity> {

	/**
	 * Retorna uma string em html com as regras para importacao
	 * */
	@SuppressWarnings("rawtypes")
	public TResult getImportRules(TAccessToken token);
	
	/**
	 * Importa o arquivo
	 * */
	@SuppressWarnings("rawtypes")
	public TResult importFile(TAccessToken token, final ITFileEntity entity);
	
}
