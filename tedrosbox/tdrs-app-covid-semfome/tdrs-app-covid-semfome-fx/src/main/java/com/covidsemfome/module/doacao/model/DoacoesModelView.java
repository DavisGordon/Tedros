/**
 * 
 */
package com.covidsemfome.module.doacao.model;

import java.util.Date;

import com.covidsemfome.model.Doacao;
import com.tedros.fxapi.collections.ITObservableList;
import com.tedros.fxapi.presenter.model.TModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
public class DoacoesModelView extends TModelView<DoacoesModel> {
	
	

	private ITObservableList<DoacaoEntityModelView> doacoes;
	
	private SimpleObjectProperty<Date> dataInicio;
	
	private SimpleObjectProperty<Date> dataFim;
	
	private SimpleStringProperty valorTotal;
	
	public DoacoesModelView(DoacoesModel model) {
		super(model);
		// TODO Auto-generated constructor stub
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
