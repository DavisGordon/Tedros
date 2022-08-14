package org.tedros.login.decorator;

import org.tedros.fx.presenter.dynamic.decorator.TDynaViewCrudBaseDecorator;
import org.tedros.fx.presenter.dynamic.view.TDynaView;
import org.tedros.login.model.LoginModelView;

public class LoginDecorator extends TDynaViewCrudBaseDecorator<LoginModelView> {

	@Override
	public void decorate() {
		
		TDynaView<LoginModelView> view = getView();
		
		view.setMaxSize(600, 560);
		
		setViewTitle(null);
		addItemInTCenterContent(view.gettFormSpace());
		buildSaveButton(null);
		buildCancelButton(null);
		addItemInTHeaderToolBar(gettSaveButton(), gettCancelButton());

	}

}
