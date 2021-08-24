/**
 * 
 */
package com.covidsemfome.module.report.model;

import java.util.Date;

import com.covidsemfome.report.model.VoluntarioItemModel;
import com.tedros.fxapi.presenter.model.TModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
public class VoluntarioItemTableView extends TModelView<VoluntarioItemModel> {
	
	private SimpleLongProperty id;
	
	private SimpleObjectProperty<Date> data;
	
	private SimpleStringProperty titulo;
		
	private SimpleStringProperty nome;
	
	private SimpleStringProperty tiposAjuda;
	
	private SimpleStringProperty email;

	private SimpleStringProperty contatos;

	public VoluntarioItemTableView(VoluntarioItemModel model) {
		super(model);
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
	 * @return the titulo
	 */
	public SimpleStringProperty getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(SimpleStringProperty titulo) {
		this.titulo = titulo;
	}

	/**
	 * @return the nome
	 */
	public SimpleStringProperty getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(SimpleStringProperty nome) {
		this.nome = nome;
	}


	/**
	 * @return the email
	 */
	public SimpleStringProperty getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(SimpleStringProperty email) {
		this.email = email;
	}

	/**
	 * @return the contatos
	 */
	public SimpleStringProperty getContatos() {
		return contatos;
	}

	/**
	 * @param contatos the contatos to set
	 */
	public void setContatos(SimpleStringProperty contatos) {
		this.contatos = contatos;
	}

	/**
	 * @return the tiposAjuda
	 */
	public SimpleStringProperty getTiposAjuda() {
		return tiposAjuda;
	}

	/**
	 * @param tiposAjuda the tiposAjuda to set
	 */
	public void setTiposAjuda(SimpleStringProperty tiposAjuda) {
		this.tiposAjuda = tiposAjuda;
	}


}
