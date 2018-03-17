/**
 * 
 */
package com.tedros.core.context;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Davis Gordon
 *
 */
public class TEntryBuilder<T> {

	private String id;
	private Map<String, T> values;
	
	private TEntryBuilder() {
		values= new HashMap<>();
	}
	
	public static <T> TEntryBuilder<T> create(){
		return new TEntryBuilder<T>();
	}
	
	public TEntryBuilder<T> id(String id){
		this.id = id;
		return this;
	}
	
	public TEntryBuilder<T> addEntry(String key, T value){
		this.values.put(key, value);
		return this;
	}
	
	public TEntry<T> build(){
		TEntry<T> entry = (StringUtils.isNotBlank(id))
				? new TEntry<T>(id)
						: new TEntry<T>();
		entry.putAll(values);
		return entry;
	}
	
	
	
}
