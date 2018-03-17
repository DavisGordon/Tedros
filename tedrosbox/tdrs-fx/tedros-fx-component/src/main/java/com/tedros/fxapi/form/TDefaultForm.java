package com.tedros.fxapi.form;

import javafx.geometry.Insets;

import com.tedros.core.model.ITModelView;
import com.tedros.core.presenter.ITPresenter;
import com.tedros.fxapi.form.TVBoxForm;

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

	@Override
	public void tInitializeForm() {
		formatForm();
	}

	@Override
	public void tInitializeReader() {
		formatForm();
		
	}
	
	private void formatForm() {
		autosize();
		setSpacing(8);
		setPadding(new Insets(10, 10, 10, 10));
		setMaxHeight(Double.MAX_VALUE);
		setMaxWidth(Double.MAX_VALUE);
		addEndSpacer();
	}

}
