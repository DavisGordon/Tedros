/**
 * 
 */
package org.somos.module.pessoa.model;

import org.somos.model.UF;

import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
public class UFModelView extends TEntityModelView<UF> {

	private SimpleLongProperty id;
	
	private SimpleStringProperty sigla;
	
	private SimpleStringProperty descricao;
	
	public UFModelView(UF entity) {
		super(entity);
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
	 * @return the sigla
	 */
	public SimpleStringProperty getSigla() {
		return sigla;
	}

	/**
	 * @param sigla the sigla to set
	 */
	public void setSigla(SimpleStringProperty sigla) {
		this.sigla = sigla;
	}

	/**
	 * @return the descricao
	 */
	public SimpleStringProperty getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(SimpleStringProperty descricao) {
		this.descricao = descricao;
	}

	@Override
	public SimpleStringProperty getDisplayProperty() {
		return descricao;
	}

}
