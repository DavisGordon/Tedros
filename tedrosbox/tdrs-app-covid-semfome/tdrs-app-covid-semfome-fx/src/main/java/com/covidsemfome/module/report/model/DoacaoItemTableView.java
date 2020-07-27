/**
 * 
 */
package com.covidsemfome.module.report.model;

import java.util.Date;

import com.covidsemfome.report.model.DoacaoItemModel;
import com.tedros.fxapi.presenter.model.TModelView;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
public class DoacaoItemTableView extends TModelView<DoacaoItemModel> {
	
	private SimpleObjectProperty<Date> data;
	
	private SimpleStringProperty tipoAjuda;
	
	private SimpleLongProperty quantidade;
	
	private SimpleDoubleProperty valor;
	
	private SimpleStringProperty pessoa;
	
	private SimpleStringProperty acao;


	public DoacaoItemTableView(DoacaoItemModel model) {
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


	/**
	 * @return the data
	 */
	public SimpleObjectProperty<Date> getData() {
		return data;
	}


	/**
	 * @param data the data to set
	 */
	public void setData(SimpleObjectProperty<Date> data) {
		this.data = data;
	}


	/**
	 * @return the tipoAjuda
	 */
	public SimpleStringProperty getTipoAjuda() {
		return tipoAjuda;
	}


	/**
	 * @param tipoAjuda the tipoAjuda to set
	 */
	public void setTipoAjuda(SimpleStringProperty tipoAjuda) {
		this.tipoAjuda = tipoAjuda;
	}


	/**
	 * @return the quantidade
	 */
	public SimpleLongProperty getQuantidade() {
		return quantidade;
	}


	/**
	 * @param quantidade the quantidade to set
	 */
	public void setQuantidade(SimpleLongProperty quantidade) {
		this.quantidade = quantidade;
	}


	/**
	 * @return the valor
	 */
	public SimpleDoubleProperty getValor() {
		return valor;
	}


	/**
	 * @param valor the valor to set
	 */
	public void setValor(SimpleDoubleProperty valor) {
		this.valor = valor;
	}


	/**
	 * @return the pessoa
	 */
	public SimpleStringProperty getPessoa() {
		return pessoa;
	}


	/**
	 * @param pessoa the pessoa to set
	 */
	public void setPessoa(SimpleStringProperty pessoa) {
		this.pessoa = pessoa;
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

}
