package com.covidsemfome.module.acao.model;

import org.apache.commons.lang3.StringUtils;

import com.tedros.fxapi.control.validator.TFieldResult;
import com.tedros.fxapi.control.validator.TValidator;

import javafx.beans.property.SimpleStringProperty;

public class MailingDestinoValidator extends TValidator {

	public MailingDestinoValidator(String label, Object value) {
		super(label, value);
	}

	@Override
	public TFieldResult validate() {
		SimpleStringProperty emails = (SimpleStringProperty) getAssociatedFieldValues().get("emails");
		SimpleStringProperty value =  (SimpleStringProperty) getValue();
		if(StringUtils.isBlank(value.getValue()) && StringUtils.isBlank(emails.getValue())){
			return new TFieldResult(getLabel(), "Favor selecionar ou preencher um destino!", false, false);
		}
		return null;
	}

}
