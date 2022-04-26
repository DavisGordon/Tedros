/**
 * 
 */
package org.somos.module.acao.campanha.model;

import org.somos.model.Campanha;

import com.tedros.fxapi.presenter.model.TModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
public class CampanhaItemTableView extends TModelView<Campanha> {
	
	private SimpleLongProperty id;
	
	private SimpleStringProperty titulo;
		
	private SimpleStringProperty status;

	public CampanhaItemTableView(Campanha model) {
		super(model);
	}
	
	@Override
	public SimpleStringProperty getDisplayProperty() {
		return titulo;
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
	 * @return the status
	 */
	public SimpleStringProperty getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(SimpleStringProperty status) {
		this.status = status;
	}

}
