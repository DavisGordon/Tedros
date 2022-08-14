package org.tedros.tools.logged.user;

import org.tedros.fx.presenter.entity.decorator.TSaveViewDecorator;

public class TUserSettingDecorator extends TSaveViewDecorator<TUserSettingModelView> {

	@Override
	public void decorate() {
		super.decorate();
		super.getView().gettProgressIndicator().setMediumLogo();
	}

}
