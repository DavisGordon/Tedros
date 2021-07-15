/**
 * 
 */
package com.solidarity.module.report.model;

import java.util.Date;

import com.solidarity.report.model.AcaoItemModel;
import com.tedros.fxapi.presenter.model.TModelView;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
public class AcaoItemTableView extends TModelView<AcaoItemModel> {
	
	private SimpleLongProperty id;
	
	private SimpleObjectProperty<Date> data;
	
	private SimpleStringProperty titulo;
		
	private SimpleIntegerProperty totalInscritos;
	
	private SimpleStringProperty status;

	public AcaoItemTableView(AcaoItemModel model) {
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
	 * @return the totalInscritos
	 */
	public SimpleIntegerProperty getTotalInscritos() {
		return totalInscritos;
	}

	/**
	 * @param totalInscritos the totalInscritos to set
	 */
	public void setTotalInscritos(SimpleIntegerProperty totalInscritos) {
		this.totalInscritos = totalInscritos;
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
