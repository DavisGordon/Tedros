/**
 * 
 */
package com.covidsemfome.module.doacao.model;

import com.covidsemfome.model.Doacao;
import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
public class DoacaoEntityModelView extends TEntityModelView<Doacao> {
	
	
	
	
	

	public DoacaoEntityModelView(Doacao model) {
		super(model);
	}
	

	@Override
	public void setId(SimpleLongProperty id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SimpleLongProperty getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		// TODO Auto-generated method stub
		return null;
	}

}
