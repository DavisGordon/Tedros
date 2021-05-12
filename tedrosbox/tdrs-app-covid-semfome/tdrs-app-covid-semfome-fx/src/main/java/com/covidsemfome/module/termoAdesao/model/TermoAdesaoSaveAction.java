/**
 * 
 */
package com.covidsemfome.module.termoAdesao.model;

import com.covidsemfome.model.TermoAdesao;
import com.tedros.fxapi.control.action.TPresenterAction;
import com.tedros.fxapi.form.ITModelForm;
import com.tedros.fxapi.presenter.dynamic.TDynaPresenter;
import com.tedros.fxapi.presenter.entity.behavior.TMasterCrudViewBehavior;
import com.tedros.fxapi.presenter.entity.decorator.TMasterCrudViewDecorator;

import javafx.scene.web.HTMLEditor;

/**
 * @author Davis Gordon
 *
 */
@SuppressWarnings("rawtypes")
public class TermoAdesaoSaveAction extends TPresenterAction<TDynaPresenter<TermoAdesaoModelView>> {

	@SuppressWarnings("unchecked")
	@Override
	public boolean runBefore(TDynaPresenter presenter) {
		
		TMasterCrudViewBehavior<TermoAdesaoModelView, TermoAdesao> b = (TMasterCrudViewBehavior<TermoAdesaoModelView, TermoAdesao>) presenter.getBehavior();
		TermoAdesaoModelView m =  b.getModelView();
		ITModelForm<TermoAdesaoModelView> form = presenter.getBehavior().getForm();
		
		String html = ((HTMLEditor)form.gettFieldBox("conteudo").gettControl()).getHtmlText();
		m.getConteudo().setValue(html);
		
		return true;
	}

	@Override
	public void runAfter(TDynaPresenter presenter) {
		TMasterCrudViewDecorator<TermoAdesaoModelView> d = (TMasterCrudViewDecorator<TermoAdesaoModelView>) presenter.getDecorator();
		TMasterCrudViewBehavior<TermoAdesaoModelView, TermoAdesao> b = (TMasterCrudViewBehavior<TermoAdesaoModelView, TermoAdesao>) presenter.getBehavior();
		//TermoAdesaoModelView m =  b.getModelView();
		int idx = d.gettListView().getSelectionModel().getSelectedIndex();
		b.remove();
		d.gettListView().getItems().clear();
		
		b.loadModels();
		d.gettListView().getSelectionModel().select(idx);
	}

}
