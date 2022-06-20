package com.tedros.tools.module.scheme.behaviour;

import com.tedros.fxapi.form.ITModelForm;
import com.tedros.fxapi.form.TDefaultForm;
import com.tedros.fxapi.form.TProgressIndicatorForm;
import com.tedros.fxapi.presenter.dynamic.behavior.TDynaViewCrudBaseBehavior;
import com.tedros.fxapi.process.TModelProcess;
import com.tedros.tools.module.scheme.decorator.TPanelDecorator;
import com.tedros.tools.module.scheme.model.TPanel;
import com.tedros.tools.module.scheme.model.TPanelMV;
import com.tedros.tools.module.scheme.style.TedrosStyleUtil;

public class TPanelBehavior extends TDynaViewCrudBaseBehavior<TPanelMV, TPanel> {
	
	@Override
	public boolean runWhenModelProcessSucceeded(TModelProcess<TPanel> modelProcess) {
		TedrosStyleUtil.applyChanges();
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
		TPanelDecorator decorator = (TPanelDecorator) getPresenter().getDecorator();
		final TDefaultForm<TPanelMV> defaultForm = (TDefaultForm<TPanelMV>) ((TProgressIndicatorForm)form).gettForm();
		//defaultForm.setId(null);
		//defaultForm.setAlignment(Pos.TOP_LEFT);
		//defaultForm.setPadding(new Insets(0));
		defaultForm.tAddAssociatedObject(ExampleBehavior.class.getSimpleName(), decorator.getExamplePresenter().getBehavior());
		((TPanelMV)getModelView()).loadSavedValues();
		
		
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
	public boolean processNewEntityBeforeBuildForm(TPanelMV model) {
		return true;
	}

}
