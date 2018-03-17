package com.tedros.fxapi.form;

import javafx.geometry.Insets;

import com.tedros.fxapi.presenter.filter.TFilterModelView;

public final class TDefaultFilterForm<F extends TFilterModelView> extends TVBoxFilterForm<F> {

	public TDefaultFilterForm(F filterView) {
		super(filterView);
	}

	@Override
	public void tInitializeForm() {
		setSpacing(8);
		setPadding(new Insets(10,10,10,10));
		setPrefHeight(USE_COMPUTED_SIZE);
		setMaxHeight(Double.MAX_VALUE);
		setMaxWidth(Double.MAX_VALUE);
		addEndSpacer();
	}

}
