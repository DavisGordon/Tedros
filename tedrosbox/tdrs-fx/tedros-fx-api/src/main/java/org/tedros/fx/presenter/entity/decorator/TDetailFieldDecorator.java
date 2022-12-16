package org.tedros.fx.presenter.entity.decorator;

import org.tedros.api.presenter.view.ITDynaView;
import org.tedros.fx.presenter.dynamic.decorator.TDetailFieldBaseDecorator;
import org.tedros.fx.presenter.model.TEntityModelView;

import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

/**
 * The decorator of the table detail view. 
 * It can be applied on detail entities.
 * A TableView is created to list the 
 * details. 
 * 
 * @author Davis Gordon
 *
 * @param <M>
 */
@SuppressWarnings("rawtypes")
public class TDetailFieldDecorator<M extends TEntityModelView> 
extends TDetailFieldBaseDecorator<M>
 {
	private VBox box;
    
	@Override
	@SuppressWarnings("unchecked")
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
