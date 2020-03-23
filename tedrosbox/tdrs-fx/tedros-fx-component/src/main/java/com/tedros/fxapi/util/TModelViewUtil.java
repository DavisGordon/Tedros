/**
 * TEDROS  
 * 
 * TODOS OS DIREITOS RESERVADOS
 * 26/02/2014
 */
package com.tedros.fxapi.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.tedros.core.model.ITModelView;
import com.tedros.ejb.base.entity.ITEntity;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public final class TModelViewUtil<M extends ITModelView<?>, E extends ITEntity> {
	
	final private Class<M> 	modelViewClass; 
	final private Class<E> 	entityClass; 
	final private List<E> 	entitys;
	
	
	public TModelViewUtil(Class<M> modelViewClass, Class<E> entityClass, List<E> entitys) {
		this.modelViewClass = modelViewClass;
		this.entityClass = entityClass;
		this.entitys = entitys;
	}
	
	public TModelViewUtil(Class<M> modelViewClass, Class<E> entityClass, E entity) {
		this.modelViewClass = modelViewClass;
		this.entityClass = entityClass;
		this.entitys = new ArrayList<>();
		this.entitys.add(entity);
	}
	
	public List<M> convertToModelViewList() {
		List<M> result = new ArrayList<>(entitys.size());
		for (E entity : entitys) {
			M modelView;
			try {
				modelView = (M) modelViewClass.getConstructor(entityClass).newInstance(entity);
				result.add(modelView);
			} catch (InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public M convertToModelView(){
		E entity = entitys.get(0);
		M modelView = null;
		try {
			modelView = (M) modelViewClass.getConstructor(entityClass).newInstance(entity);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return modelView;
	}

}
