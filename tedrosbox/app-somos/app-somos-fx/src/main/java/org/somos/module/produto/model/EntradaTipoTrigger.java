/**
 * 
 */
package org.somos.module.produto.model;

import com.tedros.fxapi.control.THorizontalRadioGroup;
import com.tedros.fxapi.control.TSelectionModal;
import com.tedros.fxapi.control.trigger.TTrigger;
import com.tedros.fxapi.form.TFieldBox;

import javafx.scene.control.Toggle;

/**
 * @author Davis Gordon
 *
 */
public class EntradaTipoTrigger extends TTrigger<Toggle> {

	/**
	 * @param source
	 * @param target
	 */
	public EntradaTipoTrigger(TFieldBox source, TFieldBox target) {
		super(source, target);
	}

	/* (non-Javadoc)
	 * @see com.tedros.fxapi.control.trigger.TTrigger#run()
	 */
	@Override
	public void run(Toggle value) {
		THorizontalRadioGroup tipo =  (THorizontalRadioGroup) getSource().gettControl();
		TFieldBox box = getTarget();
		if(tipo.getSelectedToggle()!=null){
			String data = (String) tipo.getSelectedToggle().getUserData();
			if(data.equals("Compras")) {
				TSelectionModal  sm = (TSelectionModal) box.gettControl();
				sm.gettSelectedItems().clear();
				box.setVisible(false);
			}else
				box.setVisible(true);
		}else
			box.setVisible(false);
	
	}

}
