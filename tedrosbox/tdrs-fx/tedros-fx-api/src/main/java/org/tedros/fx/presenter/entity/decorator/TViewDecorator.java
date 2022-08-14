package org.tedros.fx.presenter.entity.decorator;

import org.tedros.fx.annotation.TAnnotationDefaultValue;
import org.tedros.fx.annotation.presenter.TPresenter;
import org.tedros.fx.presenter.dynamic.decorator.TDynaViewSimpleBaseDecorator;
import org.tedros.fx.presenter.dynamic.view.TDynaView;
import org.tedros.fx.presenter.model.TModelView;

@SuppressWarnings("rawtypes")
public class TViewDecorator<M extends TModelView> 
extends TDynaViewSimpleBaseDecorator<M> {
	
	private TPresenter tPresenter;
  
    public void decorate() {
    	tPresenter = getPresenter().getPresenterAnnotation();
		configFormSpace();
		configViewTitle();
	}

	
	protected void configViewTitle() {
		if(!tPresenter.decorator().viewTitle().equals(TAnnotationDefaultValue.TVIEW_viewTitle))
			setViewTitle(null);
		else {
			((TDynaView)super.getView()).gettLayout().getChildren().remove(0);
		}
	}

	protected void configFormSpace() {
		addItemInTCenterContent(getPresenter().getView().gettFormSpace());
	}
	
	

}
