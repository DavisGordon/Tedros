package org.tedros.fx.control.trigger;

import java.util.HashMap;
import java.util.Map;

import org.tedros.api.form.ITModelForm;
import org.tedros.fx.form.TFieldBox;

public abstract class TTrigger<T> {
	
	public enum TEvent {
		BUILD, ADDED, REMOVED;
	};

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
	
	public abstract void run(TEvent event, T newValue, T oldValue);

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
