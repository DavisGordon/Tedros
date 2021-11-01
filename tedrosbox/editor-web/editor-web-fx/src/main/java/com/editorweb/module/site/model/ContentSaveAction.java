/**
 * 
 */
package com.editorweb.module.site.model;

import com.tedros.fxapi.control.action.TPresenterAction;
import com.tedros.fxapi.form.ITModelForm;
import com.tedros.fxapi.presenter.behavior.TActionType;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;

/**
 * @author Davis Gordon
 *
 */
public class ContentSaveAction extends TPresenterAction {

	public ContentSaveAction() {
		super(TActionType.SAVE);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean runBefore() {
		TDynaPresenter p = getPresenter();
		ITModelForm form = p.getBehavior().getForm();
		TContentSetting s = (TContentSetting) form.gettSetting();
		s.prepareSave();
		return true;
	}

	@Override
	public void runAfter() {
		
	}

}
