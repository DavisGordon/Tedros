package com.covidsemfome.module.empresaParceira.model;

import org.apache.commons.lang3.StringUtils;

import com.tedros.fxapi.control.validator.TFieldResult;
import com.tedros.fxapi.control.validator.TValidator;
import com.tedros.util.TValidatorUtil;

import javafx.beans.property.SimpleStringProperty;

public class EmailValidator extends TValidator {

	public EmailValidator(String label, Object value) {
		super(label, value);
	}

	@Override
	public TFieldResult validate() {
		SimpleStringProperty value =  (SimpleStringProperty) getValue();
		if(StringUtils.isNotBlank(value.getValue())){
			if(!TValidatorUtil.isEmailAddress(value.getValue()))
				return new TFieldResult(getLabel(), "Favor informar um email valido!", false, false);
		}
		return null;
	}

}
