package com.covidsemfome.module.pessoa.form;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;

import com.covidsemfome.module.pessoa.model.PessoaFilterModelView;
import com.tedros.fxapi.form.TFlowPaneFilterForm;

public class PessoaFilterForm extends TFlowPaneFilterForm<PessoaFilterModelView> {

	public PessoaFilterForm(PessoaFilterModelView filterView) {
		super(filterView);
	}

	@Override
	public void tInitializeForm() {
		setOrientation(Orientation.HORIZONTAL);
		setPrefWrapLength(900);
		setHgap(40);
		setVgap(20);
		setPadding(new Insets(10));
		autosize();
	}

}
