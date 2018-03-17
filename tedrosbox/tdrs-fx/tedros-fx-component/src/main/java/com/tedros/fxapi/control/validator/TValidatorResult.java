package com.tedros.fxapi.control.validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.tedros.core.model.ITModelView;

@SuppressWarnings("rawtypes")
public class TValidatorResult<E extends ITModelView> {
	
	private E modelView;
	private String name;
	private List<TFieldResult> fields;
	
	public TValidatorResult() {
		
	}
	
	public TValidatorResult(E modelView) {
		this.modelView = modelView;
	}
	
	public boolean isRequirementAccomplished(){
		return fields==null || (fields!=null && fields.size()==0);
	}
	
	public void addResult(TFieldResult...TFieldResults){
		if(fields == null)
			fields = new ArrayList<>();
			fields.addAll(Arrays.asList(TFieldResults));
	}
	
	public void addResult(String fieldLabel, Boolean empty){
		if(fields == null)
			fields = new ArrayList<>();
		fields.add(new TFieldResult(fieldLabel, empty));
	}
	
	public void addResult(String fieldLabel, String message, Boolean empty){
		if(fields == null)
			fields = new ArrayList<>();
		fields.add(new TFieldResult(fieldLabel, message, empty));
	}
	
	public void addResult(String fieldLabel, String message, Boolean empty, Boolean valid){
		if(fields == null)
			fields = new ArrayList<>();
		fields.add(new TFieldResult(fieldLabel, message, empty, valid));
	}
	

	public E getModelView() {
		return modelView;
	}

	public void setModelView(E modelView) {
		this.modelView = modelView;
	}
	

	public List<TFieldResult> getFields() {
		return fields;
	}

	public void setFields(List<TFieldResult> fields) {
		this.fields = fields;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
