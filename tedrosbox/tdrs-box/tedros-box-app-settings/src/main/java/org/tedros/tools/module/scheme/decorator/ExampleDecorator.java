package org.tedros.tools.module.scheme.decorator;

import org.tedros.fx.presenter.dynamic.decorator.TDynaViewCrudBaseDecorator;
import org.tedros.fx.presenter.dynamic.view.ITDynaView;
import org.tedros.tools.module.scheme.model.ExampleMV;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class ExampleDecorator extends TDynaViewCrudBaseDecorator<ExampleMV> {

	@Override
	public void decorate() {
		
		// get the view
		final ITDynaView<ExampleMV> view = getPresenter().getView();
		
		view.gettFormSpace().setPrefWidth(400);
		view.gettFormSpace().setAlignment(Pos.TOP_LEFT);
		view.gettFormSpace().setPadding(new Insets(0));
				
		addItemInTCenterContent(view.gettFormSpace());
		
		setViewTitle(null);
		buildNewButton(null);
		buildSaveButton(null);
		buildDeleteButton(null);
		
		buildModesRadioButton(null, null);
		
		addItemInTHeaderToolBar(gettNewButton(), gettSaveButton(), gettDeleteButton());
		addItemInTHeaderHorizontalLayout(gettEditModeRadio(), gettReadModeRadio());

	}

}
