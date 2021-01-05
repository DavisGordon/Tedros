/**
 * 
 */
package com.covidsemfome.module.produto.model;

import com.covidsemfome.model.SaidaItem;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.control.PopOver;
import com.tedros.fxapi.control.TSelectionModal;
import com.tedros.fxapi.control.action.TPresenterAction;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.entity.behavior.TDetailFieldBehavior;

import javafx.scene.control.Label;

/**
 * @author Davis Gordon
 *
 */
public class SaidaItemAddAction extends TPresenterAction<TDynaPresenter<SaidaItemModelView>> {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public boolean runBefore(TDynaPresenter<SaidaItemModelView> presenter) {
		
		TDetailFieldBehavior b = (TDetailFieldBehavior) presenter.getBehavior();
		final SaidaItemModelView m = (SaidaItemModelView) b.getModelView();
		ITObservableList<SaidaItemModelView> lst = (ITObservableList<SaidaItemModelView>) b.getModels();
		String n = null;
		if(m!=null && lst!=null && !lst.isEmpty()) {
			SaidaItem s1 = m.getEntity();
			if(s1.getProduto()==null)
				return true;
			for (SaidaItemModelView i : lst) {
				SaidaItem s2 = i.getEntity();
				if(s1.getProduto().getId().equals(s2.getProduto().getId())) {
					n = s2.getProduto().getNome();
					break;
				}
			}
		}
		
		if(n!=null){
			Label label = new Label("O produto "+n+" já se encontra adicionado!");
			label.setId("t-label");
			label.setStyle(	"-fx-font: Arial; "+
							"-fx-font-size: 1.0em; "+
							"-fx-font-weight: bold; "+
							"-fx-font-smoothing-type:lcd; "+
							"-fx-text-fill: #000000; "+
							"-fx-padding: 2 5 5 2; ");
			
			PopOver p = new PopOver(label);
			p.show(((TSelectionModal)b.getForm().gettFieldBox("produto").gettControl()).gettListView());
		}
		return n==null;
	}

	@Override
	public void runAfter(TDynaPresenter<SaidaItemModelView> presenter) {
		// TODO Auto-generated method stub
		
	}

}
