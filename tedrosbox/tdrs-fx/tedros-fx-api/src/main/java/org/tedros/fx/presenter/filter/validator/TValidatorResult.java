package org.tedros.fx.presenter.filter.validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.tedros.fx.control.validator.TFieldResult;
import org.tedros.fx.presenter.filter.TFilterModelView;

@SuppressWarnings("rawtypes")
public class TValidatorResult<F extends TFilterModelView> {
	
	private F filterView;
	private String name;
	private List<TFieldResult> fields;
	
	public TValidatorResult() {
		
	}
	
	public TValidatorResult(F modelView) {
		this.filterView = modelView;
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
	

	public F getFilterView() {
		return filterView;
	}

	public void setFilterView(F FilterView) {
		this.filterView = FilterView;
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
