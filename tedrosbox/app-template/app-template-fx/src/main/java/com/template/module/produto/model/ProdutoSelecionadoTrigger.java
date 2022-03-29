/**
 * 
 */
package com.template.module.produto.model;

import com.tedros.fxapi.control.TComboBoxField;
import com.tedros.fxapi.control.TSelectionModal;
import com.tedros.fxapi.control.trigger.TTrigger;
import com.tedros.fxapi.form.TFieldBox;

import javafx.collections.ListChangeListener.Change;

/**
 * @author Davis Gordon
 *
 */
public class ProdutoSelecionadoTrigger extends TTrigger<Change> {

	/**
	 * @param source
	 * @param target
	 */
	public ProdutoSelecionadoTrigger(TFieldBox source, TFieldBox target) {
		super(source, target);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.template.fxapi.control.trigger.TTrigger#run()
	 */
	@Override
	public void run(Change value) {
		TSelectionModal  sm = (TSelectionModal)  getSource().gettControl();
		TFieldBox box = getTarget();
		TComboBoxField<String>  cb = (TComboBoxField<String>) box.gettControl();
		ProdutoFindModelView data = sm.gettSelectedItems().isEmpty() 
				? null 
						: (ProdutoFindModelView) sm.gettSelectedItems().get(0);
		
		if(data!=null) {
			for(String um : cb.getItems()) {
				//String um = (String) o;
				if(um.equals(data.getEntity().getUnidadeMedida())) {
					cb.getSelectionModel().select(um);
					break;
				}
			}
		}
	
	
	}

}
