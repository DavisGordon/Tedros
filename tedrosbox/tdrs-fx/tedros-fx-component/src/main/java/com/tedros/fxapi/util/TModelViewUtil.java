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
import com.tedros.ejb.base.model.ITModel;

/**
 * DESCRIÇÃO DA CLASSE
 *
 * @author Davis Gordon
 *
 */
public final class TModelViewUtil<M extends ITModelView<?>, E extends ITModel> {
	
	final private Class<M> 	modelViewClass; 
	final private Class<E> 	modelClass; 
	final private List<E> 	models;
	
	
	public TModelViewUtil(Class<M> modelViewClass, Class<E> modelClass, List<E> models) {
		this.modelViewClass = modelViewClass;
		this.modelClass = modelClass;
		this.models = models;
	}
	
	public TModelViewUtil(Class<M> modelViewClass, Class<E> modelClass, E model) {
		this.modelViewClass = modelViewClass;
		this.modelClass = modelClass;
		this.models = new ArrayList<>();
		this.models.add(model);
	}
	
	public List<M> convertToModelViewList() {
		List<M> result = new ArrayList<>(models.size());
		for (E model : models) {
			M modelView;
			try {
				modelView = (M) modelViewClass.getConstructor(modelClass).newInstance(model);
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
		E model = models.get(0);
		M modelView = null;
		try {
			modelView = (M) modelViewClass.getConstructor(modelClass).newInstance(model);
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return modelView;
	}

	public static <T> T buildModelView(Class<T> modelViewClass, Class<? extends ITModel> modelClass){
		try {
			return modelViewClass.getConstructor(modelClass).newInstance(modelClass.newInstance());
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}
}
