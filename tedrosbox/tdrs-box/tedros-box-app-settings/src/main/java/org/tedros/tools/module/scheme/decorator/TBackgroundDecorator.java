package org.tedros.tools.module.scheme.decorator;

import org.tedros.fx.presenter.dynamic.decorator.TDynaViewCrudBaseDecorator;
import org.tedros.tools.module.scheme.model.TBackgroundImageMV;

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
