package com.solidarity.module.produto.model;

import com.tedros.core.TInternationalizationEngine;
import com.tedros.fxapi.control.validator.TFieldResult;
import com.tedros.fxapi.control.validator.TValidator;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class EntradaDoadorValidator extends TValidator {

	public EntradaDoadorValidator(String label, Object value) {
		super(label, value);
	}

	@Override
	public TFieldResult validate() {
		SimpleStringProperty tipo = (SimpleStringProperty) getAssociatedFieldValues().get("tipo");
		SimpleObjectProperty value =  (SimpleObjectProperty) getValue();
		if(tipo.get()!=null && tipo.get().equals("Doação")){
			if(value.get()==null){
				String msg = TInternationalizationEngine.getInstance(null).getString("#{msg.validar.entrada.doador}");
				return new TFieldResult(getLabel(), msg, false, false);
			}
		}
		
		return null;
	}

}
