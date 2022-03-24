package com.tedros.fxapi.control.validator;

import com.tedros.core.TLanguage;

public class TFieldResult{
	
	private String fieldLabel;
	private boolean empty;
	private boolean valid;
	private String message;
	
	private TLanguage iEngine = TLanguage.getInstance(null);
	
	public TFieldResult() {
		
	}
	
	public TFieldResult(String fieldLabel, boolean empty ) {
		setFieldLabel(fieldLabel);
		this.empty = empty;
		this.valid = empty;
	}
	
	public TFieldResult(String fieldLabel, String message, boolean empty) {
		setFieldLabel(fieldLabel);
		setMessage(message);
		this.empty = empty;
		this.valid = empty;
		
	}
	
	public TFieldResult(String fieldLabel, String message, boolean empty, boolean valid) {
		setFieldLabel(fieldLabel);
		setMessage(message);
		this.empty = empty;
		this.valid = valid;
	}

	public String getFieldLabel() {
		return fieldLabel;
	}

	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = iEngine.getString(fieldLabel);
	}

	public boolean isEmpty() {
		return empty;
	}

	public void setEmpty(boolean empty) {
		this.empty = empty;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = iEngine.getString(message);
	}
	
	
}
