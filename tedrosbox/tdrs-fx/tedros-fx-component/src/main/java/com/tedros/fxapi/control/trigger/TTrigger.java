package com.tedros.fxapi.control.trigger;

import java.util.HashMap;
import java.util.Map;

import com.tedros.fxapi.form.ITModelForm;
import com.tedros.fxapi.form.TFieldBox;

public abstract class TTrigger {

	private TFieldBox source;
	private TFieldBox target;
	private ITModelForm<?> form;
	
	private Map<String, TFieldBox> associatedFields;
	
	
	public TTrigger(TFieldBox source, TFieldBox target) {
		this.source = source;
		this.target = target;
	}
	
	public void addAssociatedField(TFieldBox fieldBox){
		
		if(fieldBox==null)
			throw new IllegalArgumentException("Method addAssociatedField(TFieldBox fieldBox) in TTrigger.class dont accept null values!");
		
		if(associatedFields == null)
			associatedFields = new HashMap<String, TFieldBox>();
		associatedFields.put(fieldBox.gettControlFieldName(), fieldBox);
	}
	
	public TFieldBox getAssociatedFieldBox(String fieldName){
		if(associatedFields == null || fieldName == null)
			return null;
		return associatedFields.get(fieldName);
	}
	
	public abstract void run();

	public TFieldBox getSource() {
		return source;
	}
	public TFieldBox getTarget() {
		return target;
	}

	public ITModelForm<?> getForm() {
		return form;
	}

	public void setForm(ITModelForm<?> form) {
		this.form = form;
	}
	
	
}
