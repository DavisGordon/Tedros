/**
 * 
 */
package com.covidsemfome.module.report.model;

import com.covidsemfome.report.model.EstocavelModel;
import com.tedros.fxapi.presenter.model.TModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
public class EstocavelTableView extends TModelView<EstocavelModel> {
	
	private SimpleLongProperty id;
	
	private SimpleStringProperty  acao;
	
	private SimpleStringProperty cozinha;
	
	private SimpleStringProperty dataHora;
	
	private SimpleStringProperty  tipo;
	
	private SimpleStringProperty  doador;
	
	public EstocavelTableView(EstocavelModel model) {
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
	 * @return the acao
	 */
	public SimpleStringProperty getAcao() {
		return acao;
	}

	/**
	 * @param acao the acao to set
	 */
	public void setAcao(SimpleStringProperty acao) {
		this.acao = acao;
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

	/**
	 * @return the tipo
	 */
	public SimpleStringProperty getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(SimpleStringProperty tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the doador
	 */
	public SimpleStringProperty getDoador() {
		return doador;
	}

	/**
	 * @param doador the doador to set
	 */
	public void setDoador(SimpleStringProperty doador) {
		this.doador = doador;
	}

}
