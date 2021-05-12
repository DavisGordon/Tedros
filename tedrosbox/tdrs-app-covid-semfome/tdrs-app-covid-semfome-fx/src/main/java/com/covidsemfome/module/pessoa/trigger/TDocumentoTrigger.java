package com.covidsemfome.module.pessoa.trigger;

import com.tedros.fxapi.control.TDatePickerField;
import com.tedros.fxapi.control.THorizontalRadioGroup;
import com.tedros.fxapi.control.TMaskField;
import com.tedros.fxapi.control.TTextField;
import com.tedros.fxapi.control.trigger.TTrigger;
import com.tedros.fxapi.form.TFieldBox;

import javafx.scene.Node;
import javafx.scene.control.Toggle;

public class TDocumentoTrigger extends TTrigger<Toggle> {

	public TDocumentoTrigger(TFieldBox source, TFieldBox target) {
		super(source, target);
	}

	@Override
	public void run(Toggle toogle) {
		THorizontalRadioGroup tipoDoc = (THorizontalRadioGroup) getSource().gettControl();
		
		if(tipoDoc==null)
			return;
		
		TMaskField documentoField = (TMaskField) getTarget().gettControl();
		
		if(tipoDoc.getSelectedToggle()==null)
			return;
		
		String data = (String) tipoDoc.getSelectedToggle().getUserData();
		
		if(data==null)
			return;
		
		TFieldBox nacionalidadeBox = getAssociatedFieldBox("nacionalidade");
		TFieldBox dataValidadeBox = getAssociatedFieldBox("dataValidade");
		TFieldBox dataEmissaoBox = getAssociatedFieldBox("dataEmissao");
		TFieldBox orgaoEmissorBox = getAssociatedFieldBox("orgaoEmissor");
		
		int tipo = Integer.parseInt(data);
		switch (tipo) {
			case 1 :
				documentoField.setMask("##########");
				habilitar(nacionalidadeBox, dataValidadeBox, dataEmissaoBox, orgaoEmissorBox);
				break;

			case 2:
				documentoField.setMask("999.999.999-99");
				desabilitar(nacionalidadeBox,dataValidadeBox, dataEmissaoBox, orgaoEmissorBox);
				
				break;
			case 3:
				documentoField.setMask("99.999.999/9999-99");
				desabilitar(nacionalidadeBox, dataValidadeBox, dataEmissaoBox, orgaoEmissorBox);
				break;
		}

	}

	private void desabilitar(TFieldBox... fieldBox) {
		for(TFieldBox box: fieldBox)
			if(box!=null) {
				clear(box);
				box.setDisable(true);
			}
	}
	
	private void habilitar(TFieldBox... fieldBox) {
		for(TFieldBox box: fieldBox)
			if(box!=null) 
				box.setDisable(false);
	}

	
	private void clear(TFieldBox fieldBox) {
		Node control = fieldBox.gettControl();
		if(control instanceof TTextField){
			((TTextField) control).setText(null);
		}
		if(control instanceof TDatePickerField){
			((TDatePickerField)control).setValue(null);
		}
		
	}

}
