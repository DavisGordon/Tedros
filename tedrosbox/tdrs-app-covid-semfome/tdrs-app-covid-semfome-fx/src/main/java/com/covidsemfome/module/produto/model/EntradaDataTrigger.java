/**
 * 
 */
package com.covidsemfome.module.produto.model;

import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.control.TDatePickerField;
import com.tedros.fxapi.control.trigger.TTrigger;
import com.tedros.fxapi.form.TFieldBox;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.dynamic.view.TDynaView;

import javafx.scene.control.TabPane;

/**
 * @author Davis Gordon
 *
 */
public class EntradaDataTrigger extends TTrigger {

	/**
	 * @param source
	 * @param target
	 */
	public EntradaDataTrigger(TFieldBox source, TFieldBox target) {
		super(source, target);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.tedros.fxapi.control.trigger.TTrigger#run()
	 */
	@Override
	public void run() {
		TDatePickerField data =  (TDatePickerField) getSource().gettControl();
		TabPane tp = (TabPane) getTarget().gettControl();
		TDynaView view = (TDynaView) tp.getTabs().get(0).getContent();
		ITObservableList<EntradaItemModelView> itens = (ITObservableList<EntradaItemModelView>) ((TDynaPresenter)view.gettPresenter()).getBehavior().getModels();
		
		if(itens!=null) {
			itens.forEach(m -> {
				m.getData().setValue(data.getValue());
			});
		}
	
	
	}

}
