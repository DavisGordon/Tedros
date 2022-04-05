package com.tedros.fxapi.presenter.entity.decorator;

import com.tedros.fxapi.annotation.TAnnotationDefaultValue;
import com.tedros.fxapi.annotation.presenter.TPresenter;
import com.tedros.fxapi.presenter.dynamic.decorator.TDynaViewSimpleBaseDecorator;
import com.tedros.fxapi.presenter.dynamic.view.TDynaView;
import com.tedros.fxapi.presenter.model.TModelView;

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
