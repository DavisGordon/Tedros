package org.tedros.tools.module.scheme.behaviour;

import org.tedros.fx.form.ITModelForm;
import org.tedros.fx.form.TDefaultForm;
import org.tedros.fx.form.TProgressIndicatorForm;
import org.tedros.fx.presenter.dynamic.behavior.TDynaViewCrudBaseBehavior;
import org.tedros.fx.process.TModelProcess;
import org.tedros.tools.module.scheme.decorator.TPanelDecorator;
import org.tedros.tools.module.scheme.model.TPanel;
import org.tedros.tools.module.scheme.model.TPanelMV;
import org.tedros.tools.module.scheme.style.TedrosStyleUtil;

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
