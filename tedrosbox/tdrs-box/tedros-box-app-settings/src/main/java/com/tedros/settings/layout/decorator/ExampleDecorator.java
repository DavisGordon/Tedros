package com.tedros.settings.layout.decorator;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import com.tedros.fxapi.presenter.dynamic.decorator.TDynaViewCrudBaseDecorator;
import com.tedros.fxapi.presenter.dynamic.view.TDynaView;
import com.tedros.settings.layout.model.ExampleViewModel;

public class ExampleDecorator extends TDynaViewCrudBaseDecorator<ExampleViewModel> {

	@SuppressWarnings("unchecked")
	@Override
	public void decorate() {
		
		// get the view
		final TDynaView<ExampleViewModel> view = getPresenter().getView();
		
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
