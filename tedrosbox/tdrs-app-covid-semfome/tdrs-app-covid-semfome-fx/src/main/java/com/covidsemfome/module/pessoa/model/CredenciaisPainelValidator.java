package com.covidsemfome.module.pessoa.model;

import org.apache.commons.lang3.StringUtils;

import com.tedros.fxapi.control.validator.TFieldResult;
import com.tedros.fxapi.control.validator.TValidator;
import com.tedros.util.TEmailUtil;

import javafx.beans.property.SimpleStringProperty;

public class CredenciaisPainelValidator extends TValidator {

	public CredenciaisPainelValidator(String label, Object value) {
		super(label, value);
	}

	@Override
	public TFieldResult validate() {
		SimpleStringProperty password = (SimpleStringProperty) getAssociatedFieldValues().get("password");
		SimpleStringProperty value =  (SimpleStringProperty) getValue();
		if(StringUtils.isNotBlank(value.getValue()) && StringUtils.isBlank(password.getValue())){
			return new TFieldResult(getLabel(), "Favor informar o password para o login informado!", false, false);
		}
		if(StringUtils.isBlank(value.getValue()) && StringUtils.isNotBlank(password.getValue())){
			return new TFieldResult(getLabel(), "Favor informar o email de login para o password informado!", false, false);
		}
		if(StringUtils.isNotBlank(value.getValue())){
			if(!TEmailUtil.validate(value.getValue()))
				return new TFieldResult(getLabel(), "Favor informar um email valido!", false, false);
		}
		return null;
	}

}
