package org.somos.module.mailing.model;

import org.apache.commons.lang3.StringUtils;

import com.tedros.fxapi.control.validator.TFieldResult;
import com.tedros.fxapi.control.validator.TValidator;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class MailingDestinoValidator extends TValidator {

	public MailingDestinoValidator(String label, Object value) {
		super(label, value);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public TFieldResult validate() {
		SimpleStringProperty emails = (SimpleStringProperty) getAssociatedFieldValues().get("emails");
		SimpleObjectProperty value =  (SimpleObjectProperty) getValue();
		if(value.getValue()==null && StringUtils.isBlank(emails.getValue())){
			return new TFieldResult(getLabel(), "Favor selecionar ou preencher um destino!", false, false);
		}
		return null;
	}

}
