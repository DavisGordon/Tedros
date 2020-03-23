package com.tedros.fxapi.control.validator;

import java.util.HashMap;
import java.util.Map;

public abstract class TValidator {
	
	private Map<String, Object> associatedFieldValues;
	private final Object value;
	private final String label;
	
	public TValidator(final String label, final Object value) {
		this.label = label;
		this.value = value;
	}
	
	public void addAssociatedFieldValue(final String fieldName, final Object value) {
		if(this.associatedFieldValues==null)
			this.associatedFieldValues = new HashMap<String, Object>(0);
		this.associatedFieldValues.put(fieldName, value);
	}
	
	public abstract TFieldResult validate();
	
	public Map<String, Object> getAssociatedFieldValues() {
		return associatedFieldValues;
	}
	
	public Object getValue() {
		return value;
	}
	
	public String getLabel() {
		return label;
	}

}
