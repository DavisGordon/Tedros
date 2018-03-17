package com.tedros.core.model;

import java.util.Map;
import java.util.Set;

import com.tedros.core.module.TListenerRepository;
import com.tedros.ejb.base.model.ITModel;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.SetChangeListener;

public interface ITModelView<M extends ITModel> {
	
	public void removeAllListener();
	
	public <T> T removeListener(String listenerId);
	
	public Map<String, Set<String>> getListenerKeys();
	
	public TListenerRepository getListenerRepository();
	
	/**
	 * @param fieldName
	 * @param invalidationListener
	 */
	public void addListener(final String fieldName, InvalidationListener invalidationListener);
	
	/**
	 * @param fieldName
	 * @param changeListener
	 */
	@SuppressWarnings("rawtypes")
	public void addListener(final String fieldName, ChangeListener changeListener);
	
	/**
	 * @param fieldName
	 * @param changeListener
	 */
	@SuppressWarnings("rawtypes")
	public void addListener(final String fieldName, final MapChangeListener changeListener);
	
	/**
	 * @param fieldName
	 * @param changeListener
	 */
	@SuppressWarnings("rawtypes")
	public void addListener(final String fieldName, final SetChangeListener changeListener);

	/**
	 * @param fieldName
	 * @param changeListener
	 */
	@SuppressWarnings("rawtypes")
	public void addListener(final String fieldName, final ListChangeListener changeListener);

}