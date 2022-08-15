/**
 * 
 */
package org.tedros.core.repository;

import java.util.HashMap;
import java.util.Map;

/**
 * An object repository
 * 
 * @author Davis Gordon
 */
public class TRepository {
	
	private Map<String, Object> repository;
	
	public TRepository() {
		repository = new HashMap<>();
	}
	
	/**
	 * Returns the listener by the key 
	 * */
	@SuppressWarnings("unchecked")
	public <T> T get(String key){
		return (T) repository.get(key);
	}
	
	/**
	 * Removes the listener by the key 
	 * */
	@SuppressWarnings("unchecked")
	public <T> T remove(String key){
		return (T) repository.remove(key);
	}
	
	/**
	 * Adds the listener to the repository  
	 * */
	public void add(String key, Object obj) throws TKeyAlreadyExistException, IllegalArgumentException{
		
		if(repository.containsKey(key))
			throw new TKeyAlreadyExistException();
		
		repository.put(key, obj);
	}
	
	public void clear() {
		repository.clear();
	}
	
}
