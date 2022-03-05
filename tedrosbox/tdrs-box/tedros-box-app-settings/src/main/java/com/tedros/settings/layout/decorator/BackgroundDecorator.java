package com.tedros.settings.layout.decorator;

import com.tedros.fxapi.presenter.dynamic.decorator.TDynaViewCrudBaseDecorator;
import com.tedros.settings.layout.model.BackgroundImageModelView;

public class BackgroundDecorator extends TDynaViewCrudBaseDecorator<BackgroundImageModelView> {

	@Override
	public void decorate() {
		
		try {
			setViewTitle(null);
			super.addItemInTCenterContent(getView().gettFormSpace());
			super.buildSaveButton(null);
			super.addItemInTHeaderToolBar(super.gettSaveButton());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
