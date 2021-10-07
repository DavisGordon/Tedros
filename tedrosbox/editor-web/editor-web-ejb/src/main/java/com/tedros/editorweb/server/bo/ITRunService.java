package com.tedros.editorweb.server.bo;

import java.util.function.Consumer;

import com.tedros.editorweb.server.service.TEntityService;
import com.tedros.ejb.base.entity.ITEntity;

public interface ITRunService<E extends ITEntity> {
	
	ITRunBO<E> getRunBO();

	void runNonTx(Consumer<TEntityService<E>> f);
	
	void runTx(Consumer<TEntityService<E>> f);
	
}
