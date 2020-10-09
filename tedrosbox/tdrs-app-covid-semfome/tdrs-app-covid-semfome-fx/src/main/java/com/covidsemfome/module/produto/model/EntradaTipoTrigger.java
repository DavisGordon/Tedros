/**
 * 
 */
package com.covidsemfome.module.produto.model;

import com.tedros.fxapi.control.THorizontalRadioGroup;
import com.tedros.fxapi.control.TSelectionModal;
import com.tedros.fxapi.control.trigger.TTrigger;
import com.tedros.fxapi.form.TFieldBox;

/**
 * @author Davis Gordon
 *
 */
public class EntradaTipoTrigger extends TTrigger {

	/**
	 * @param source
	 * @param target
	 */
	public EntradaTipoTrigger(TFieldBox source, TFieldBox target) {
		super(source, target);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.tedros.fxapi.control.trigger.TTrigger#run()
	 */
	@Override
	public void run() {
		THorizontalRadioGroup tipo =  (THorizontalRadioGroup) getSource().gettControl();
		TFieldBox box = getTarget();
		TSelectionModal  sm = (TSelectionModal) box.gettControl();
		String data = (tipo.getSelectedToggle()!=null) 
				? (String) tipo.getSelectedToggle().getUserData() :
					null;
		
		if(data!=null && data.equals("Compras")) {
			sm.gettSelectedItems().clear();
			box.setVisible(false);
		}else
			box.setVisible(true);
	
	
	}

}
