package org.tedros.fx.form;

import org.tedros.core.model.ITModelView;
import org.tedros.core.presenter.ITPresenter;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;

public final class TDefaultDetailForm<M extends ITModelView<?>> extends TFlowPaneForm<M> {

	public TDefaultDetailForm(M modelView) {
		super(modelView);
	}
	
	public TDefaultDetailForm(M modelView, Boolean readerMode) {
		super(modelView, readerMode);
	}
	
	@SuppressWarnings("rawtypes")
	public TDefaultDetailForm(ITPresenter presenter, M modelView) {
		super(presenter, modelView);
	}
	
	@SuppressWarnings("rawtypes")
	public TDefaultDetailForm(ITPresenter presenter, M modelView, Boolean readerMode) {
		super(presenter, modelView, readerMode);
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
		setAlignment(Pos.TOP_LEFT);
		setHgap(8);
		setVgap(8);
		setOrientation(Orientation.HORIZONTAL);
		setPrefWrapLength(USE_COMPUTED_SIZE);
		setPadding(new Insets(10, 10, 10, 10));
		//setPrefHeight(USE_COMPUTED_SIZE);
		//setMaxHeight(Double.MAX_VALUE);
		//setMaxWidth(Double.MAX_VALUE);
		
	}

}
