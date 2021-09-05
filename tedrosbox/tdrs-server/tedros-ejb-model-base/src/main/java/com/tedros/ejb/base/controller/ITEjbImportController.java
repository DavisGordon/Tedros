package com.tedros.ejb.base.controller;

import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.ejb.base.entity.ITFileEntity;
import com.tedros.ejb.base.result.TResult;
import com.tedros.ejb.base.security.TAccessToken;

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
