package com.tedros.fxapi.form;

import com.tedros.core.model.ITModelView;
import com.tedros.core.presenter.ITPresenter;

public final class TDefaultForm<M extends ITModelView<?>> extends TVBoxForm<M> {

	public TDefaultForm(M modelView) {
		super(modelView);
	}
	
	public TDefaultForm(M modelView, Boolean reader ) {
		super(modelView, reader);
	}
	
	@SuppressWarnings("rawtypes")
	public TDefaultForm(ITPresenter presenter, M modelView) {
		super(presenter, modelView);
	}
	
	@SuppressWarnings("rawtypes")
	public TDefaultForm(ITPresenter presenter, M modelView, Boolean reader ) {
		super(presenter, modelView, reader);
	}

	

}
