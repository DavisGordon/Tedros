package com.tedros.editorweb.server.bo;

import java.util.function.Consumer;

import com.tedros.ejb.base.bo.ITGenericBO;
import com.tedros.ejb.base.entity.ITEntity;

public interface ITRunBO<E extends ITEntity> {

	void run(Consumer<ITGenericBO<E>> bo);
	
}
