package com.tedros.settings.layout.form;


import java.math.BigInteger;
import java.util.Date;

import com.tedros.ejb.base.entity.TEntity;

public class EntityExampleForm extends TEntity {

	private static final long serialVersionUID = -5789123411402469912L;
	
	
	private Long longField;
	private String stringField;
	private String textField;
	private String passField;
	private Boolean booleanField;
	private Date dateField;
	private Double doubleField;
	private Integer integerField;
	private BigInteger bigIntegerField;
	
	public EntityExampleForm() {
		
		setId(22L);
		longField = 35L;
		stringField = "John Doe";
		textField = "";
		booleanField = true;
		doubleField = 22.02D;
		
	}
	
	@Override
	public String toString() {
		return (getStringField()!=null) ? getStringField() : "";
	}

	public Long getLongField() {
		return longField;
	}

	public void setLongField(Long longField) {
		this.longField = longField;
	}

	public String getStringField() {
		return stringField;
	}

	public void setStringField(String stringField) {
		this.stringField = stringField;
	}

	public String getTextField() {
		return textField;
	}

	public void setTextField(String textField) {
		this.textField = textField;
	}

	public String getPassField() {
		return passField;
	}

	public void setPassField(String passField) {
		this.passField = passField;
	}

	public Boolean getBooleanField() {
		return booleanField;
	}

	public void setBooleanField(Boolean booleanField) {
		this.booleanField = booleanField;
	}

	public Double getDoubleField() {
		return doubleField;
	}

	public void setDoubleField(Double doubleField) {
		this.doubleField = doubleField;
	}

	public Integer getIntegerField() {
		return integerField;
	}

	public void setIntegerField(Integer integerField) {
		this.integerField = integerField;
	}

	public BigInteger getBigIntegerField() {
		return bigIntegerField;
	}

	public void setBigIntegerField(BigInteger bigIntegerField) {
		this.bigIntegerField = bigIntegerField;
	}

	public Date getDateField() {
		return dateField;
	}

	public void setDateField(Date dateField) {
		this.dateField = dateField;
	}
	
	@Override
	public Long getId() {
		return super.getId();
	}
	
	@Override
	public void setId(Long id) {
		super.setId(id);
	}
	
	

	
}
