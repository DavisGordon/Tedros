/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 26/02/2014
 */
package org.tedros.fx.presenter.model;

import java.lang.reflect.InvocationTargetException;

import org.tedros.core.model.ITModelView;
import org.tedros.fx.exception.TException;
import org.tedros.server.entity.ITEntity;
import org.tedros.server.model.ITModel;

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
			return (M) this.modelViewClass.getConstructor(entity.getClass()).newInstance(entity);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new TException(e);
		}
	}
	

	@SuppressWarnings("unchecked")
	public final M build(ITModel model) throws TException{
		try {
			return (M) this.modelViewClass.getConstructor(model.getClass()).newInstance(model);
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
