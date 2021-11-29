/**
 * 
 */
package com.covidsemfome.module.empresaParceira.model;

import com.tedros.fxapi.control.action.TPresenterAction;
import com.tedros.fxapi.form.ITModelForm;
import com.tedros.fxapi.presenter.behavior.TActionType;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;

/**
 * @author Davis Gordon
 *
 */
public class SiteConteudoSaveAction extends TPresenterAction {

	public SiteConteudoSaveAction() {
		super(TActionType.SAVE);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean runBefore() {
		TDynaPresenter p = getPresenter();
		ITModelForm form = p.getBehavior().getForm();
		SiteConteudoSetting s = (SiteConteudoSetting) form.gettSetting();
		s.prepareSave();
		return true;
	}

	@Override
	public void runAfter() {
		
	}

}
