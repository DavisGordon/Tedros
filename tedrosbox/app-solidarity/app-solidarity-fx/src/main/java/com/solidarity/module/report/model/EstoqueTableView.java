/**
 * 
 */
package com.solidarity.module.report.model;

import com.solidarity.report.model.EstoqueModel;
import com.tedros.fxapi.presenter.model.TModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
public class EstoqueTableView extends TModelView<EstoqueModel> {
	
	private SimpleLongProperty id;
	
	private SimpleStringProperty  origem;
	
	private SimpleStringProperty cozinha;
	
	private SimpleStringProperty dataHora;
	
	public EstoqueTableView(EstoqueModel model) {
		super(model);
	}
	
	@Override
	public SimpleStringProperty getDisplayProperty() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the id
	 */
	public SimpleLongProperty getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(SimpleLongProperty id) {
		this.id = id;
	}

	/**
	 * @return the origem
	 */
	public SimpleStringProperty getOrigem() {
		return origem;
	}

	/**
	 * @param origem the origem to set
	 */
	public void setOrigem(SimpleStringProperty origem) {
		this.origem = origem;
	}

	/**
	 * @return the cozinha
	 */
	public SimpleStringProperty getCozinha() {
		return cozinha;
	}

	/**
	 * @param cozinha the cozinha to set
	 */
	public void setCozinha(SimpleStringProperty cozinha) {
		this.cozinha = cozinha;
	}

	/**
	 * @return the dataHora
	 */
	public SimpleStringProperty getDataHora() {
		return dataHora;
	}

	/**
	 * @param dataHora the dataHora to set
	 */
	public void setDataHora(SimpleStringProperty dataHora) {
		this.dataHora = dataHora;
	}


}
