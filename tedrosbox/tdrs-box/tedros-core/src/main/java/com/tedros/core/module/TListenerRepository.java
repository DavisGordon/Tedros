/**
 * 
 */
package com.tedros.core.module;

import java.util.HashMap;
import java.util.Map;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.SetChangeListener;

/**
 * A listener repository
 * 
 * @author Davis Gordon
 */
public class TListenerRepository {
	
	private Map<String, Object> repository;
	
	public TListenerRepository() {
		repository = new HashMap<>();
	}
	
	/**
	 * Returns the listener by the key 
	 * */
	@SuppressWarnings("unchecked")
	public <T> T getListener(String key){
		return (T) repository.get(key);
	}
	
	/**
	 * Removes the listener by the key 
	 * */
	@SuppressWarnings("unchecked")
	public <T> T removeListener(String key){
		return (T) repository.remove(key);
	}
	
	/**
	 * Adds the listener to the repository  
	 * */
	public void addListener(String key, Object listener) throws TKeyAlreadyExistException, IllegalArgumentException{
		
		if(!(listener instanceof ChangeListener 
				|| listener instanceof InvalidationListener 
				|| listener instanceof SetChangeListener 
				|| listener instanceof ListChangeListener 
				|| listener instanceof MapChangeListener))
			throw new IllegalArgumentException("TERROR: the argument passed is not a valid listener: "
					+ "ChangeListener, InvalidationListener, SetChangeListener, ListChangeListener or MapChangeListener.");
			
		
		if(repository.containsKey(key))
			throw new TKeyAlreadyExistException();
		
		repository.put(key, listener);
	}
	
}
