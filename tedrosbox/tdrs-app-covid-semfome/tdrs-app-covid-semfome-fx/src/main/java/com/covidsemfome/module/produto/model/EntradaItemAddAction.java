/**
 * 
 */
package com.covidsemfome.module.produto.model;

import com.covidsemfome.model.EntradaItem;
import com.tedros.core.control.PopOver;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.control.TSelectionModal;
import com.tedros.fxapi.control.action.TPresenterAction;
import com.tedros.fxapi.presenter.behavior.TActionType;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.entity.behavior.TDetailFieldBehavior;

import javafx.scene.control.Label;

/**
 * @author Davis Gordon
 *
 */
public class EntradaItemAddAction extends TPresenterAction {

	public EntradaItemAddAction() {
		super(TActionType.ADD);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public boolean runBefore() {
		TDynaPresenter<EntradaItemModelView> presenter = getPresenter();
		TDetailFieldBehavior b = (TDetailFieldBehavior) presenter.getBehavior();
		final EntradaItemModelView m = (EntradaItemModelView) b.getModelView();
		ITObservableList<EntradaItemModelView> lst = (ITObservableList<EntradaItemModelView>) b.getModels();
		String n = null;
		if(m!=null && lst!=null && !lst.isEmpty()) {
			EntradaItem s1 = m.getEntity();
			if(s1.getProduto()==null)
				return true;
			for (EntradaItemModelView i : lst) {
				EntradaItem s2 = i.getEntity();
				if(s1.getProduto().getId().equals(s2.getProduto().getId())) {
					n = s2.getProduto().getNome();
					break;
				}
			}
		}
		
		if(n!=null) {
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
	public void runAfter() {
		
	}

}
