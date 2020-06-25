package com.covidsemfome.module.doador.trigger;

import com.tedros.fxapi.control.TDatePickerField;
import com.tedros.fxapi.control.THorizontalRadioGroup;
import com.tedros.fxapi.control.TMaskField;
import com.tedros.fxapi.control.TTextField;
import com.tedros.fxapi.control.trigger.TTrigger;
import com.tedros.fxapi.form.TFieldBox;

public class TDocumentoTrigger extends TTrigger {

	public TDocumentoTrigger(TFieldBox source, TFieldBox target) {
		super(source, target);
	}

	@Override
	public void run() {
		THorizontalRadioGroup tipoDoc = (THorizontalRadioGroup) getSource().gettControl();
		
		if(tipoDoc==null)
			return;
		
		TMaskField documentoField = (TMaskField) getTarget().gettControl();
		
		if(tipoDoc.getSelectedToggle()==null)
			return;
		
		String data = (String) tipoDoc.getSelectedToggle().getUserData();
		
		if(data==null)
			return;
		
		TFieldBox dataValidadeBox = getAssociatedFieldBox("dataValidade");
		TFieldBox dataEmissaoBox = getAssociatedFieldBox("dataEmissao");
		TFieldBox orgaoEmissorBox = getAssociatedFieldBox("orgaoEmissor");
		
		int tipo = Integer.parseInt(data);
		switch (tipo) {
			case 1 :
				documentoField.setMask("##########");
				desabilitar(dataValidadeBox, dataEmissaoBox, orgaoEmissorBox);
				break;

			case 2:
				documentoField.setMask("999.999.999-99");
				habilitar(dataValidadeBox, dataEmissaoBox, orgaoEmissorBox);
				
				break;
			case 3:
				documentoField.setMask("99.999.999/9999-99");
				habilitar(dataValidadeBox, dataEmissaoBox,
						orgaoEmissorBox);
				break;
		}

	}

	private void desabilitar(TFieldBox dataValidadeBox,
			TFieldBox dataEmissaoBox, TFieldBox orgaoEmissorBox) {
		if(dataValidadeBox!=null)
			dataValidadeBox.setDisable(false);
		if(dataEmissaoBox!=null)
			dataEmissaoBox.setDisable(false);
		if(orgaoEmissorBox!=null)
			orgaoEmissorBox.setDisable(false);
	}

	
	private void habilitar(TFieldBox dataValidadeBox,
			TFieldBox dataEmissaoBox, TFieldBox orgaoEmissorBox) {
		
		if(dataValidadeBox!=null){
			((TDatePickerField)dataValidadeBox.gettControl()).setValue(null);
			dataValidadeBox.setDisable(true);
		}
		
		if(dataEmissaoBox!=null){
			((TDatePickerField)dataEmissaoBox.gettControl()).setValue(null);
			dataEmissaoBox.setDisable(true);
		}
		if(orgaoEmissorBox!=null){
			((TTextField) orgaoEmissorBox.gettControl()).setText(null);
			orgaoEmissorBox.setDisable(true);
		}
	}

}
