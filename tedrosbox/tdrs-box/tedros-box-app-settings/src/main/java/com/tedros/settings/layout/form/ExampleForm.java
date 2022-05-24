package com.tedros.settings.layout.form;

import javafx.geometry.Insets;

import com.tedros.core.presenter.ITPresenter;
import com.tedros.fxapi.form.TVBoxForm;
import com.tedros.settings.layout.model.ExampleMV;

public class ExampleForm extends TVBoxForm<ExampleMV> {

	public ExampleForm() {
		super(new ExampleMV());
	}
	
	public ExampleForm(ExampleMV model) {
		super(model);
	}
	
	public ExampleForm(ExampleMV model, Boolean reader) {
		super(model, reader);
	}
	
	@SuppressWarnings("rawtypes")
	public ExampleForm(ITPresenter presenter, ExampleMV model) {
		super(presenter, model);
	}
	
	@SuppressWarnings("rawtypes")
	public ExampleForm(ITPresenter presenter, ExampleMV model, Boolean reader) {
		super(presenter, model, reader);
	}

	@Override
	public void tInitializeForm() {
		init();
	}

	@Override
	public void tInitializeReader() {
		init();
	}
	
	private void init() {
		setFillWidth(true);
		setMaxWidth(Double.MAX_VALUE);
		setPadding(new Insets(40));
		//setMinHeight(1100);
		setSpacing(10);
		addEndSpacer();
	}

	@Override
	public String toString() {
		return "ExampleForm, total itens: "+getChildren().size();
	}
}
