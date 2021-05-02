package com.tedros.fxapi.presenter.entity.decorator;

import com.tedros.fxapi.presenter.dynamic.decorator.TDetailFieldBaseDecorator;
import com.tedros.fxapi.presenter.dynamic.view.ITDynaView;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

@SuppressWarnings("rawtypes")
public class TDetailFieldDecorator<M extends TEntityModelView> 
extends TDetailFieldBaseDecorator<M>
 {
	private VBox box;
    
	public void decorate() {
		
		// get the views
		final ITDynaView<M> view = getPresenter().getView();
		
		box = new VBox();
		box.getChildren().add(view.gettFormSpace());
		addItemInTCenterContent(box);
		setViewTitle(null);
		
		buildAddButton(null);
		buildRemoveButton(null);
		buildCleanButton(null);
		addItemInTHeaderToolBar(gettAddButton(), gettRemoveButton(), gettCleanButton());

	}
	@Override
	public void settTableView(TableView<M> tv) {
		super.settTableView(tv);
		box.getChildren().add(tv);
	}
	

}
