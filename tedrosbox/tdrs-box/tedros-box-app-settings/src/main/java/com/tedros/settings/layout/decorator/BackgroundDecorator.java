package com.tedros.settings.layout.decorator;

import com.tedros.fxapi.presenter.dynamic.decorator.TDynaViewCrudBaseDecorator;
import com.tedros.settings.layout.model.BackgroundImageModelView;
import com.tedros.settings.layout.view.EditarFundoView;

public class BackgroundDecorator extends TDynaViewCrudBaseDecorator<BackgroundImageModelView> {

	@Override
	public void decorate() {
		
		try {
			setViewTitle(null);
			addItemInTCenterContent(new EditarFundoView());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
