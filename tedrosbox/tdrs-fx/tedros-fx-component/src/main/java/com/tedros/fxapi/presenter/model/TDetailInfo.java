package com.tedros.fxapi.presenter.model;

import com.tedros.ejb.base.entity.ITEntity;
import com.tedros.fxapi.form.ITModelForm;

import javafx.beans.Observable;

/**
 * <pre>
 * </pre>
 * */
public class TDetailInfo{
	final private String formTitle;
	final private String listTitle;
	final private Observable property;
	@SuppressWarnings("rawtypes")
	final private Class<? extends TEntityModelView> modelViewClass;
	final private Class<? extends ITEntity> entityClass;
	@SuppressWarnings("rawtypes")
	final private Class<? extends ITModelForm> formClass;
	
	@SuppressWarnings("rawtypes")
	public TDetailInfo(String formTitle, String listTitle, Observable property, Class<? extends TEntityModelView> modelViewClass, Class<? extends ITEntity> entityClass, Class<? extends ITModelForm> formClass) {
		this.formTitle = formTitle;
		this.listTitle = listTitle; 
		this.property = property;
		this.modelViewClass = modelViewClass;
		this.entityClass = entityClass;
		this.formClass = formClass; 
	}
	
	public String getFormTitle() {
		return formTitle;
	}
	
	public String getListTitle() {
		return listTitle;
	}

	public Observable getProperty() {
		return property;
	}

	@SuppressWarnings("rawtypes")
	public Class<? extends TEntityModelView> getModelViewClass() {
		return modelViewClass;
	}

	public Class<? extends ITEntity> getEntityClass() {
		return entityClass;
	}
	
	@SuppressWarnings("rawtypes")
	public Class<? extends ITModelForm> getFormClass() {
		return formClass;
	}
}