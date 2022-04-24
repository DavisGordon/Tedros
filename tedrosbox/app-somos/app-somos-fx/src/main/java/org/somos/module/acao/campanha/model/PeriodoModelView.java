/**
 * 
 */
package org.somos.module.acao.campanha.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.somos.model.Periodo;

import com.tedros.fxapi.presenter.model.TEntityModelView;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Davis Gordon
 *
 */
public class PeriodoModelView extends TEntityModelView<Periodo> {

	private SimpleLongProperty id;
	
	private SimpleStringProperty nome;
	
	public PeriodoModelView(Periodo entity) {
		super(entity);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof PeriodoModelView))
			return false;
		return EqualsBuilder.reflectionEquals(this.getModel(), obj != null ? ((PeriodoModelView)obj).getModel() : obj, false);
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

	@Override
	public SimpleStringProperty getDisplayProperty() {
		return nome;
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

}
