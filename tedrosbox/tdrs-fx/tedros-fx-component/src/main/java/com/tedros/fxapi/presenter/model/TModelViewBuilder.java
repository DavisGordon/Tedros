/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 26/02/2014
 */
package com.tedros.fxapi.presenter.model;

import java.lang.reflect.InvocationTargetException;

import com.tedros.core.model.ITModelView;
import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.ejb.base.model.ITModel;
import com.tedros.fxapi.exception.TException;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public final class TModelViewBuilder<M extends ITModelView<?>, E extends ITModel> {
	
	private Class<? extends ITModelView<? extends ITModel>> modelViewClass;
	private Class<? extends ITModel> entityClass;
	
	@SuppressWarnings("rawtypes")
	public static TModelViewBuilder<?, ?> create(){
		TModelViewBuilder<?, ?> builder = new TModelViewBuilder();
		return builder;
	}

	public final TModelViewBuilder<M, E> modelViewClass(Class<? extends ITModelView<?>> modelViewClass) {
		this.modelViewClass = modelViewClass;
		return this;
	}

	public final TModelViewBuilder<M, E> entityClass(Class<? extends ITEntity> entityClass) {
		this.entityClass = entityClass;
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public final M build(ITEntity entity) throws TException{
		try {
			return (M) this.modelViewClass.getConstructor(entityClass).newInstance(entity);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new TException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public final M build() throws TException{
		
		try {
			return (M) (entityClass!=null 
					? this.modelViewClass.getConstructor(entityClass).newInstance(entityClass.newInstance())
							: this.modelViewClass.newInstance());
						
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new TException(e);
		}
	}
	
	

}
