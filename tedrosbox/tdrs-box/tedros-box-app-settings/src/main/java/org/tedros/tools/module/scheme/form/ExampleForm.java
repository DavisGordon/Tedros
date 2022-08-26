package org.tedros.tools.module.scheme.form;

import org.tedros.api.presenter.ITPresenter;
import org.tedros.fx.form.TVBoxForm;
import org.tedros.tools.module.scheme.model.ExampleMV;

import javafx.geometry.Insets;

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
