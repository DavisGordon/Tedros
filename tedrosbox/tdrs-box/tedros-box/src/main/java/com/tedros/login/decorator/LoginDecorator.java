package com.tedros.login.decorator;

import com.tedros.fxapi.presenter.dynamic.decorator.TDynaViewCrudBaseDecorator;
import com.tedros.fxapi.presenter.dynamic.view.TDynaView;
import com.tedros.login.model.LoginModelView;

public class LoginDecorator extends TDynaViewCrudBaseDecorator<LoginModelView> {

	@Override
	public void decorate() {
		
		TDynaView<LoginModelView> view = getView();
		
		view.setMaxSize(550, 470);
		
		setViewTitle(null);
		addItemInTCenterContent(view.gettFormSpace());
		buildSaveButton(null);
		buildCancelButton(null);
		addItemInTHeaderToolBar(gettSaveButton(), gettCancelButton());

	}

}
