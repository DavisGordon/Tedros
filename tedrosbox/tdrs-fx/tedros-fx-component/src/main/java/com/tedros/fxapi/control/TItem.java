package com.tedros.fxapi.control;

public class TItem {
	
	private String text;
	private Object value;
	
	public TItem(String text, Object value) {
		this.text = text;
		this.value = value;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj==null || value==null)
			return false;
		return value.equals(obj);
	}
	
	@Override
	public String toString() {
		return text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
