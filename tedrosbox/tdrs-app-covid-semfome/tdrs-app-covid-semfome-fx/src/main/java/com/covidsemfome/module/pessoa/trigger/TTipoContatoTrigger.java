package com.covidsemfome.module.pessoa.trigger;

import com.tedros.fxapi.control.TMaskField;
import com.tedros.fxapi.control.TVerticalRadioGroup;
import com.tedros.fxapi.control.trigger.TTrigger;
import com.tedros.fxapi.form.TFieldBox;


public class TTipoContatoTrigger extends TTrigger {

	public TTipoContatoTrigger(TFieldBox source, TFieldBox target) {
		super(source, target);
	}

	@Override
	public void run() {
		TVerticalRadioGroup vRadio = (TVerticalRadioGroup) getSource().gettControl();
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
