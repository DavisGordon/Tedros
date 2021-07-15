package com.solidarity.module.pessoa.trigger;

import com.tedros.fxapi.control.THorizontalRadioGroup;
import com.tedros.fxapi.control.TMaskField;
import com.tedros.fxapi.control.trigger.TTrigger;
import com.tedros.fxapi.form.TFieldBox;

import javafx.scene.control.Toggle;


public class TTipoContatoTrigger extends TTrigger<Toggle> {

	public TTipoContatoTrigger(TFieldBox source, TFieldBox target) {
		super(source, target);
	}

	@Override
	public void run(Toggle toggle) {
		THorizontalRadioGroup vRadio = (THorizontalRadioGroup) getSource().gettControl();
		if(vRadio==null || vRadio.getSelectedToggle()==null || vRadio.getSelectedToggle().getUserData()==null)
			return;
		
		TMaskField maskField = (TMaskField) getTarget().gettControl();
		String value = (String) vRadio.getSelectedToggle().getUserData();
		if(value.equals("1"))
			maskField.setMask("##################################################");
		else
			maskField.setMask("(99) #9999-9999");
		
	}

}
