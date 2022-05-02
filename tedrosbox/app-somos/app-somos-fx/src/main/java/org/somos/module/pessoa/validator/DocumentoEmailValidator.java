package org.somos.module.pessoa.validator;

import org.apache.commons.lang3.StringUtils;

import com.tedros.fxapi.control.validator.TFieldResult;
import com.tedros.fxapi.control.validator.TValidator;
import com.tedros.util.TValidatorUtil;

import javafx.beans.property.SimpleStringProperty;

public class DocumentoEmailValidator extends TValidator {

	public DocumentoEmailValidator(String label, Object value) {
		super(label, value);
	}

	@Override
	public TFieldResult validate() {
		SimpleStringProperty tipo = (SimpleStringProperty) getAssociatedFieldValues().get("tipo");
		SimpleStringProperty value =  (SimpleStringProperty) getValue();
		
		if(StringUtils.isNotBlank(value.getValue()) 
				&& (StringUtils.isNotBlank(tipo.getValue()) && tipo.getValue().equals("1"))){
			if(!TValidatorUtil.isEmailAddress(value.getValue()))
				return new TFieldResult(getLabel(), "Favor informar um email valido!", false, false);
		}
		return null;
	}

}
