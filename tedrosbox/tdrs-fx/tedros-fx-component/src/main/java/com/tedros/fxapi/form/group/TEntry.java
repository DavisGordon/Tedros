/**
 * 
 */
package com.tedros.fxapi.form.group;

import java.util.HashMap;
import java.util.UUID;

import org.apache.commons.lang3.builder.EqualsBuilder;

import javafx.scene.Node;

/**
 * @author Davis Gordon
 *
 */
public class TEntry extends HashMap<String, Node> {
	
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
	
	public void addEntry(String key, Node value){
		put(key, value);
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(obj==null)
			return false;
		
		TEntry item = (TEntry) obj;
		
		if(this.id!=null && item.getId()!=null)
			return this.id.equals(item.getId());
		
		return EqualsBuilder.reflectionEquals(this, obj, false);
	}
	

}
