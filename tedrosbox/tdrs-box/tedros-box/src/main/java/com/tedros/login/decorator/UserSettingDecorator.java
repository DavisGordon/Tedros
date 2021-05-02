package com.tedros.login.decorator;

import com.tedros.fxapi.presenter.dynamic.decorator.TDynaViewCrudBaseDecorator;
import com.tedros.fxapi.presenter.dynamic.view.TDynaView;
import com.tedros.login.model.UserSettingModelView;

public class UserSettingDecorator extends TDynaViewCrudBaseDecorator<UserSettingModelView> {

	@Override
	public void decorate() {
		
		TDynaView<UserSettingModelView> view = getView();
		
		
		setViewTitle(null);
		addItemInTCenterContent(view.gettFormSpace());
		buildSaveButton(null);
		
		addItemInTHeaderToolBar(gettSaveButton());

	}

}
