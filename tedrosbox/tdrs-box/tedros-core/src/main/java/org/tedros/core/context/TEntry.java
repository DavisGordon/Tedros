/**
 * 
 */
package org.tedros.core.context;

import java.util.HashMap;
import java.util.UUID;

import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * A Map with an indetification
 * 
 * @author Davis Gordon
 *
 */
public class TEntry<T> extends HashMap<String, T> {
	
	private static final long serialVersionUID = -6011942673781236286L;
	
	private String id;
	
	public TEntry() {
		this.id = UUID.randomUUID().toString();
	}
	
	public TEntry(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public void addEntry(String key, T value){
		put(key, value);
	}
	
	public T getEntry(String key){
		return get(key);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		
		if(obj==null)
			return false;
		
		TEntry<T> item = (TEntry<T>) obj;
		
		if(this.id!=null && item.getId()!=null)
			return this.id.equals(item.getId());
		
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}
	

}
