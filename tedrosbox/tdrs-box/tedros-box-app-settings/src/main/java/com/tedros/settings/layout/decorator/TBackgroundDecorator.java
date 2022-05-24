package com.tedros.settings.layout.decorator;

import com.tedros.fxapi.presenter.dynamic.decorator.TDynaViewCrudBaseDecorator;
import com.tedros.settings.layout.model.TBackgroundImageMV;

public class TBackgroundDecorator extends TDynaViewCrudBaseDecorator<TBackgroundImageMV> {

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
