package com.tedros.settings.layout.behavior;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import com.tedros.fxapi.control.action.TPresenterAction;
import com.tedros.fxapi.form.TDefaultForm;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.dynamic.behavior.TDynaViewCrudBaseBehavior;
import com.tedros.fxapi.process.TModelProcess;
import com.tedros.settings.layout.TedrosStyle;
import com.tedros.settings.layout.decorator.TedrosSettingDecorator;
import com.tedros.settings.layout.model.PainelModel;
import com.tedros.settings.layout.model.PainelModelView;

public class TedrosSettingBehavior extends TDynaViewCrudBaseBehavior<PainelModelView, PainelModel> {
	
	@Override
	public boolean runWhenModelProcessSucceeded(TModelProcess<PainelModel> modelProcess) {
		TedrosStyle.applyChanges();
		return true;
	}
	

	@Override
	public void load() {
		
		super.load();
		configSaveButton();
		setSkipChangeValidation(true);
		setSkipRequiredValidation(true);
		
		newAction();
		
		TedrosSettingDecorator decorator = (TedrosSettingDecorator) getPresenter().getDecorator();
		
		final TDefaultForm<PainelModelView> defaultForm = (TDefaultForm<PainelModelView>) getForm();
		defaultForm.setId(null);
		defaultForm.setAlignment(Pos.TOP_LEFT);
		defaultForm.setPadding(new Insets(0));
		defaultForm.tAddAssociatedObject(ExampleBehavior.class.getSimpleName(), decorator.getExamplePresenter().getBehavior());
	}
	

	@Override
	public void colapseAction() {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}


	@Override
	public void setNewEntity(PainelModelView model) {
		((PainelModelView)getModelView()).loadSavedValues();
	}

}
