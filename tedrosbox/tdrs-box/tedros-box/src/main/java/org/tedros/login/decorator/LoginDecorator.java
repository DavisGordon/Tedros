package org.tedros.login.decorator;

import org.tedros.fx.presenter.dynamic.decorator.TDynaViewCrudBaseDecorator;
import org.tedros.fx.presenter.dynamic.view.TDynaView;
import org.tedros.login.model.LoginMV;

public class LoginDecorator extends TDynaViewCrudBaseDecorator<LoginMV> {

	@Override
	public void decorate() {
		
		TDynaView<LoginMV> view = getView();
		
		view.setMaxSize(600, 560);
		
		setViewTitle(null);
		addItemInTCenterContent(view.gettFormSpace());
		buildSaveButton(null);
		buildCancelButton(null);
		addItemInTHeaderToolBar(gettSaveButton(), gettCancelButton());

	}

}
