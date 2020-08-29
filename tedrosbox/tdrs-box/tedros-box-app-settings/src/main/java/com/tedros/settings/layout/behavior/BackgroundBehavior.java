package com.tedros.settings.layout.behavior;

import com.tedros.fxapi.presenter.dynamic.behavior.TDynaViewCrudBaseBehavior;
import com.tedros.settings.layout.model.BackgroundImageModel;
import com.tedros.settings.layout.model.BackgroundImageModelView;

public class BackgroundBehavior extends TDynaViewCrudBaseBehavior<BackgroundImageModelView, BackgroundImageModel> {

	@Override
	public void colapseAction() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean processNewEntityBeforeBuildForm(BackgroundImageModelView model) {
		return true;

	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

}
