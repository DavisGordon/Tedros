package com.tedros.tools.logged.user;

import com.tedros.fxapi.presenter.entity.decorator.TSaveViewDecorator;

public class TUserSettingDecorator extends TSaveViewDecorator<TUserSettingModelView> {

	@Override
	public void decorate() {
		super.decorate();
		super.getView().gettProgressIndicator().setMediumLogo();
	}

}
