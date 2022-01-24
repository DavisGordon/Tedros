/**
 * 
 */
package org.somos.module.report.model;

import org.somos.report.model.ProdutoItemModel;

import com.tedros.fxapi.presenter.model.TModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
public class ProdutoItemTableView extends TModelView<ProdutoItemModel> {
	
	private SimpleLongProperty id;
	
	private SimpleStringProperty codigo;
		
	private SimpleStringProperty nome;
	
	private SimpleStringProperty marca;
	
	private SimpleStringProperty medida;

	private SimpleStringProperty unidadeMedida;

	public ProdutoItemTableView(ProdutoItemModel model) {
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
	 * @return the codigo
	 */
	public SimpleStringProperty getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(SimpleStringProperty codigo) {
		this.codigo = codigo;
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
	 * @return the marca
	 */
	public SimpleStringProperty getMarca() {
		return marca;
	}

	/**
	 * @param marca the marca to set
	 */
	public void setMarca(SimpleStringProperty marca) {
		this.marca = marca;
	}

	/**
	 * @return the medida
	 */
	public SimpleStringProperty getMedida() {
		return medida;
	}

	/**
	 * @param medida the medida to set
	 */
	public void setMedida(SimpleStringProperty medida) {
		this.medida = medida;
	}

	/**
	 * @return the unidadeMedida
	 */
	public SimpleStringProperty getUnidadeMedida() {
		return unidadeMedida;
	}

	/**
	 * @param unidadeMedida the unidadeMedida to set
	 */
	public void setUnidadeMedida(SimpleStringProperty unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}

}
