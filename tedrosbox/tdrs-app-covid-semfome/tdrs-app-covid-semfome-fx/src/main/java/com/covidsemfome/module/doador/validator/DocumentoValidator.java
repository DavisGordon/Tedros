package com.covidsemfome.module.doador.validator;

import org.apache.commons.lang3.StringUtils;

import javafx.beans.property.SimpleStringProperty;

import com.tedros.fxapi.control.validator.TFieldResult;
import com.tedros.fxapi.control.validator.TValidator;
import com.tedros.util.TDocumentoUtil;

public class DocumentoValidator extends TValidator {

	public DocumentoValidator(String label, Object value) {
		super(label, value);
	}

	@Override
	public TFieldResult validate() {
		SimpleStringProperty tipo = (SimpleStringProperty) getAssociatedFieldValues().get("tipo");
		SimpleStringProperty value =  (SimpleStringProperty) getValue();
		if(tipo.get()!=null && tipo.get().equals("2")){
			if(StringUtils.isBlank(value.get()) || !TDocumentoUtil.validarCPF(value.get())){
				return new TFieldResult(getLabel(), "CPF "+value.get()+" invalido", false, false);
			}
		}
		
		if(tipo.get()!=null && tipo.get().equals("3")){
			if(StringUtils.isBlank(value.get()) || !TDocumentoUtil.validarCNPJ(value.get())){
				return new TFieldResult(getLabel(), "CNPJ "+value.get()+" invalido", false, false);
			}
		}
		
		return null;
	}

}
