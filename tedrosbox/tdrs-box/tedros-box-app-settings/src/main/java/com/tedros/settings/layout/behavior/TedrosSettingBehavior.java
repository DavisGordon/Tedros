package com.tedros.settings.layout.behavior;

import com.tedros.fxapi.form.ITModelForm;
import com.tedros.fxapi.form.TDefaultForm;
import com.tedros.fxapi.form.TProgressIndicatorForm;
import com.tedros.fxapi.presenter.dynamic.behavior.TDynaViewCrudBaseBehavior;
import com.tedros.fxapi.process.TModelProcess;
import com.tedros.settings.layout.TedrosStyle;
import com.tedros.settings.layout.decorator.TedrosSettingDecorator;
import com.tedros.settings.layout.model.PainelModel;
import com.tedros.settings.layout.model.PainelModelView;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

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
		configNewButton();
		setSkipChangeValidation(true);
		setSkipRequiredValidation(true);
		
		newAction();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void runAfterBuildForm(ITModelForm form) {
		TedrosSettingDecorator decorator = (TedrosSettingDecorator) getPresenter().getDecorator();
		final TDefaultForm<PainelModelView> defaultForm = (TDefaultForm<PainelModelView>) ((TProgressIndicatorForm)form).gettForm();
		//defaultForm.setId(null);
		//defaultForm.setAlignment(Pos.TOP_LEFT);
		//defaultForm.setPadding(new Insets(0));
		defaultForm.tAddAssociatedObject(ExampleBehavior.class.getSimpleName(), decorator.getExamplePresenter().getBehavior());
		((PainelModelView)getModelView()).loadSavedValues();
		
		
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
	public boolean processNewEntityBeforeBuildForm(PainelModelView model) {
		return true;
	}

}
