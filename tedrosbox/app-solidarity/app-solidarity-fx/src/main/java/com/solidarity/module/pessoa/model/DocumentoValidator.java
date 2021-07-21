package com.solidarity.module.pessoa.model;

import org.apache.commons.lang3.StringUtils;

import com.tedros.core.TInternationalizationEngine;
import com.tedros.fxapi.control.validator.TFieldResult;
import com.tedros.fxapi.control.validator.TValidator;
import com.tedros.util.TEmailUtil;

import javafx.beans.property.SimpleStringProperty;

public class DocumentoValidator extends TValidator {

	public DocumentoValidator(String label, Object value) {
		super(label, value);
	}

	@Override
	public TFieldResult validate() {
		SimpleStringProperty tipo = (SimpleStringProperty) getAssociatedFieldValues().get("tipo");
		SimpleStringProperty value =  (SimpleStringProperty) getValue();
		
		if(StringUtils.isNotBlank(value.getValue()) 
				&& (StringUtils.isNotBlank(tipo.getValue()) && tipo.getValue().equals("1"))){
			if(!TEmailUtil.validate(value.getValue()))
				return new TFieldResult(getLabel(), 
						TInternationalizationEngine.getInstance(null).getString("#{msg.validar.email}"), false, false);
		}
		return null;
	}

}
